package com.froso.ufp.modules.core.templatesv2.service;

import com.froso.ufp.core.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.service.util.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.globals.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.model.velocity.*;
import com.froso.ufp.modules.optional.media.model.media.*;
import com.froso.ufp.modules.optional.media.service.*;
import com.froso.ufp.modules.optional.media.service.util.*;
import org.apache.commons.io.*;
import org.apache.commons.lang3.*;
import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.apache.velocity.context.*;
import org.apache.velocity.runtime.*;
import org.apache.velocity.tools.*;
import org.apache.velocity.tools.generic.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * The type Image service.
 */
@Service

public class TemplateService extends AbstractFileServiceLocalFilesSystem<FileTemplate> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateService.class);
    private static final String DEPACK_PATH = "out";
    private final ServerService serverService;
    private final GlobalsService globalsService;
    private final TemplateGlobalsService templateGlobalsService;
    private final GlobalsServiceImpl globalsServiceImplImpl;
    private final ApplicationPropertyService applicationPropertyService;
    private ToolManager toolManager;

    @Autowired
    public TemplateService(ServerService serverService, GlobalsService globalsService, TemplateGlobalsService templateGlobalsService, GlobalsServiceImpl globalsServiceImplImpl, ApplicationPropertyService applicationPropertyService) {

        super(FileTemplate.TYPE_NAME);

        this.serverService = serverService;
        this.globalsService = globalsService;
        this.templateGlobalsService = templateGlobalsService;
        this.globalsServiceImplImpl = globalsServiceImplImpl;
        this.applicationPropertyService = applicationPropertyService;
    }

    @Override
    protected boolean isOfTypeAndSet(MediaElement mediaElement, byte[] bytes) {
        FileTypeDeterminer fileTypePicture = new FileTypeZipTemplate();
        return fileTypePicture.isOfTypeAndSet(mediaElement, bytes);
    }
//
//    @Override
//    protected void postProcess(FileTemplate file) {
//        // here we decompress the uploaded zip
//        byte[] buffer = new byte[1024];
//        try {
//            InputStream inputStream =fileReadRaw(file.getMediaElement().getMediaHostFileName());
//            byte[] bytes = IOUtils.toByteArray(inputStream);
//            inputStream.close();
//
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//            ZipArchiveInputStream zis;
//
//            //get the zip file content
//            zis = new ZipArchiveInputStream(byteArrayInputStream);
//            //get the zipped file list entry
//            ArchiveEntry ze = zis.getNextEntry();
//
//            if (!isFileExistant(getWorkingPath(file))) {
//
//                makeDir(getWorkingPath(file));
//            }
//            while (ze != null) {
//
//                String fileName = ze.getName();
//
//                LOGGER.info("ZIP Content: " + fileName);
//                if (ze.isDirectory()) {
//                    makeDir(getWorkingPath(file) + "/" + ze.getName());
//                } else {
//                    // create and upload file to destination
//                    ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//                    int len;
//                    while ((len = zis.read(buffer)) > 0) {
//                        os.write(buffer, 0, len);
//                    }
//
//                    os.close();
//                    InputStream isFromFirstData = new ByteArrayInputStream(os.toByteArray());
//
//                    LOGGER.info("UPLOADING ZIP Content to " + getWorkingPath(file) + "/" + ze.getName());
//                    createElementFromInputStreamFile()
//                    session.write(isFromFirstData, getWorkingPath(file) + "/" + ze.getName());
//                }
//                //create all non exists folders
//                //else you will hit FileNotFoundException for compressed folder
//
//                ze = zis.getNextEntry();
//            }
//
//            LOGGER.error("Template Depacking Finished ");
//            //zis.closeEntry();
//            zis.close();
//
//        } catch (Exception e) {
//            LOGGER.error("Template Post Process Error ", e);
//        }
//
//    }

    private String getWorkingPath(FileTemplate file) {
        return folderUtil.getOriginalsPath(file) + "/" + DEPACK_PATH;
    }

    private Context getContext(VelocityEngine engine) {
        if (toolManager == null) {

            toolManager = new ToolManager();
            toolManager.setUserCanOverwriteTools(true);
            toolManager.autoConfigure(true);
            toolManager.setVelocityEngine(engine);
        }
        Context result;

        result = new VelocityContext();

        result.put("esc", new EscapeTool());
        result.put("link", new LinkTool());
        result.put("date", new VelocityDateFormatter());
        result.put("dateTool", new DateTool());
        result.put("alt", new AlternatorTool());
        result.put("display", new DisplayTool());
        result.put("math", new MathTool());
        result.put("loop", new LoopTool());
        result.put("number", new NumberTool());
        result.put("sort", new SortTool());
        result.put("esc", new EscapeTool());
        result.put("price", new VelocityPriceFormatter());
        result.put("globals", globalsService.getAllProperties());
        result.put("server", serverService.getServerSettings());

        return result;

    }

    private VelocityEngine getVelocityEngine(FileTemplate file) throws Exception {

        VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "templateftp");
        velocityEngine.setProperty("templateftp.resource.loader.instance", new VelocityResourceLoaderTemplatesV2(file, this));
        // Disable Loading of default Macro Library
        velocityEngine.setProperty(RuntimeConstants.VM_LIBRARY, StringUtils.EMPTY);
        velocityEngine.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        velocityEngine.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");

        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        velocityEngine.setProperty("runtime.log.logsystem.log4j.category", "velocity");
        velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", "velocity");

        Properties velocityProperties = new Properties();
        //   velocityProperties.put("file.resource.loader.path", args[0] + '/' + args[1] + "/" + args[2] + "/templates/");
        velocityEngine.init(velocityProperties);
        return velocityEngine;

    }

    @Override

    public FileTemplate findOne(String id) {
        if ("Default".equals(id)) {
            FileTemplate template = new FileTemplate();
            template.setId("Default");
            try {
//                HttpServletRequest request = RequestRemoteHostRetriever.getCurrentRequest();
//                LOGGER.info("REQUEST INFO " + request.getContextPath());
//                LOGGER.info("REQUEST INFO " + request.getPathTranslated());
//                LOGGER.info("REQUEST INFO " + request.getPathInfo());
//                LOGGER.info("REQUEST INFO " + request.getRequestURL());
//                LOGGER.info("REQUEST INFO " + request.getRemoteHost());
//                LOGGER.info("REQUEST INFO " + request.getServletPath());

                template.setLivePath(RequestRemoteHostRetriever.getApiBasePath() + "/" + UFPConstants.API + "/" + FileTemplate.TYPE_NAME + "/" + template.getId());
            } catch (Exception e) {
                LOGGER.error("Template Parser Problem, not in request mode .... failed to set livePath ", e);
            }
            return template;

        } else {
            return super.findOne(id)
                    ;
        }
    }

    /**
     * Parse template string.
     *
     * @param id       the id
     * @param entry    the entry
     * @param data     the data
     * @param settings the settings
     * @return the string
     */
    public String parseTemplate(String id, String entry, Map<String, Object> data, TemplateSettings settings) {
        FileTemplate template = findOne(id);
        String result = StringUtils.EMPTY;
        if (settings.getLanguageFileName() != null) {
            template.getTemplateSettings().setLanguageFileName(settings.getLanguageFileName());
        }
        if (settings.getTemplatePath() != null) {
            template.getTemplateSettings().setTemplatePath(settings.getTemplatePath());
        }
        if (settings.getLayoutMain() != null) {
            template.getTemplateSettings().setLayoutMain(settings.getLayoutMain());
        }

        String infix = "/";
        if (entry.indexOf(".vm") != -1) {

            infix += "templates/";
            result = parseTemplate(template, settings.getTemplatePath() + infix + entry, data);

        } else {

            result = getFileString(template, settings.getTemplatePath() + infix + entry);

        }
        return result;
    }

    /**
     * Parse template bytes byte [ ].
     *
     * @param id       the id
     * @param entry    the entry
     * @param data     the data
     * @param settings the settings
     * @return the byte [ ]
     */
    public byte[] parseTemplateBytes(String id, String entry, Map<String, Object> data, TemplateSettings settings) {
        FileTemplate template = findOne(id);
        byte[] result;
        if (settings.getLanguageFileName() != null) {
            template.getTemplateSettings().setLanguageFileName(settings.getLanguageFileName());
        }
        if (settings.getTemplatePath() != null) {
            template.getTemplateSettings().setTemplatePath(settings.getTemplatePath());
        }
        if (settings.getLayoutMain() != null) {
            template.getTemplateSettings().setLayoutMain(settings.getLayoutMain());
        }

        String infix = "/";
        if (entry.indexOf(".vm") != -1) {

            infix += "templates/";
            result = (parseTemplate(template, settings.getTemplatePath() + infix + entry, data)).getBytes();

        } else {

            result = getFileByte(template, settings.getTemplatePath() + infix + entry);

        }
        return result;
    }

    private Map<String, String> getQueryParameters(HttpServletRequest request) {
        Map<String, String> queryParameters = new HashMap<>();
        String queryString = request.getQueryString();

        if (StringUtils.isEmpty(queryString)) {
            return queryParameters;
        }

        String[] parameters = queryString.split("&");

        for (String parameter : parameters) {
            String[] keyValuePair = parameter.split("=");
            //    String[] values = queryParameters.get(keyValuePair[0]);
            //   values = ArrayUtils.add(values, keyValuePair.length == 1 ? "" : keyValuePair[1]); //length is one if no value is available.
            queryParameters.put(keyValuePair[0], keyValuePair[1]);
        }
        return queryParameters;
    }

    private Map<String, Object> getUFPTools(FileTemplate fileTemplate) {
        Map<String, Object> result = new HashMap<>();

        result.put("pathTool", new PathTool(fileTemplate.getLivePath(), StringUtils.EMPTY));
        result.put("templateGlobals", templateGlobalsService.getData());
        result.put("globals", globalsServiceImplImpl.getGlobals3());
        //   result.put("requestParams", getQueryParameters(RequestRemoteHostRetriever.getCurrentRequest()));

        return result;
    }

    private String parseTemplate(FileTemplate template, String entry, Map<String, Object> data) {
        LOGGER.info("Parsing Template", template);
        StringWriter writer = new StringWriter();
        try {
            VelocityEngine engine = getVelocityEngine(template);

            Context velocityContext = getContext(engine);

            velocityContext.put("ufp", getUFPTools(template));

            Map<String, Object> templateSettings = new HashMap<>();
            templateSettings.put("baseDir", getBasePath());
            templateSettings.put("settings", template.getTemplateSettings());
            templateSettings.put("commonsPath", getBasePath() + "/" + getWorkingPath(template) + "/" + template.getTemplateSettings().getTemplatePath() + "/commons");
            templateSettings.put("publicPath", getBasePath() + "/" + getWorkingPath(template) + "/" + template.getTemplateSettings().getTemplatePath());
            velocityContext.put("template", templateSettings);
            velocityContext.put("data", data);
            Map<String, String> controllerMap = new HashMap<>();
            String baseName = FilenameUtils.getPath(template.getMediaElement().getLivePath());
            controllerMap.put("web", baseName + DEPACK_PATH + "/templates");
            velocityContext.put("controller", controllerMap);

            velocityContext.put("config", applicationPropertyService.getProperties().get("Current"));

            String fileContent = getFileString(template, entry);
            engine.evaluate(velocityContext, writer, entry, fileContent);
        } catch (Exception e) {

            LOGGER.error("parseTemplat Error: ", e);
            writer.append(e.getMessage());
        }
//        LOGGER.info("PARSED TEMPLATE IS" + writer.toString());
        return writer.toString();

    }

    /**
     * Gets file string.
     *
     * @param template the template
     * @param entry    the entry
     * @return the file string
     */
    public String getFileString(FileTemplate template, String entry) {

        LOGGER.info("getFileString Returning ", template, entry);
        String result = StringUtils.EMPTY;
        byte[] bytes = getFileByte(template, entry);
        try {
            result = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOGGER.error("getFileString Exception ", e);
        }

        return result;

    }

    /**
     * Get file byte bytes [ ].
     *
     * @param template the template
     * @param entry    the entry
     * @return the bytes [ ]
     */
    public byte[] getFileByte(FileTemplate template, String entry) {

        byte[] result;
        /*Session session = null;
        try {
            session = createNewSession("readFile");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            session.read(getWorkingPath(template) + "/" + entry, outputStream);
            byte[] bytes = outputStream.toByteArray();

            result = bytes;


        } catch (Exception e) {
            LOGGER.warn("parseTemplate Exception " + e.getMessage());
            // try loading template from default stored in resources folder

                */

        try {
//            LOGGER.info("parseTemplate trying to use default from filesystem");
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            String fileName = "templates/default/" + entry;
            LOGGER.info("Reading file {}", fileName);
            InputStream is = classloader.getResourceAsStream(fileName);

            result = IOUtils.toByteArray(is);
        } catch (Exception e2) {
            result = ("File not found: " + entry + "\n").getBytes(StandardCharsets.UTF_8);
        }

        return result;

    }

}
