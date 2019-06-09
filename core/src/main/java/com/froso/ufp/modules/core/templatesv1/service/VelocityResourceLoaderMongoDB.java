package com.froso.ufp.modules.core.templatesv1.service;

import com.froso.ufp.modules.core.templatesv1.model.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import org.apache.commons.lang3.*;
import org.apache.velocity.exception.*;
import org.apache.velocity.runtime.resource.*;
import org.apache.velocity.runtime.resource.loader.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 23.10.2014.
 */
public class VelocityResourceLoaderMongoDB extends ResourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityResourceLoaderMongoDB.class);
    private static final String CORPORATEID_FIELD = "corporateid";
    private static String PATH_PREFIX = "templates/velocity-ibs/default/web/templates/";
    private ClasspathResourceLoader classpathResourceLoader;
    private SimpleTemplateService simpleTemplateService;

    /**
     * Constructor Velocity resource loader mongo dB.
     *
     * @param pages the pages
     */
    public VelocityResourceLoaderMongoDB(SimpleTemplateService pages) {

        simpleTemplateService = pages;
        classpathResourceLoader = new ClasspathResourceLoader();
    }

    /**
     * Init void.
     *
     * @param extendedProperties the extended properties
     */
    @Override
    public void init(org.apache.commons.collections.ExtendedProperties extendedProperties) {

    }

    /**
     * Gets resource stream.
     *
     * @param s the s
     * @return the resource stream
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Override
    public InputStream getResourceStream(String sIn) throws ResourceNotFoundException {
        String s = PATH_PREFIX + sIn;
        String corporateId = StringUtils.EMPTY;
        StringBuilder inputFilenameBuilder = new StringBuilder();
        // Check if corporateId is encoded in inputstring
        if (s.contains(CORPORATEID_FIELD)) {
            String[] pathcomponents = s.split("/");
            int foundAt = -100; // initialise in a way the loop does not get confused
            for (int i = 0; i < pathcomponents.length; i++) {
                if (CORPORATEID_FIELD.equals(pathcomponents[i])) {
                    foundAt = i;
                } else if (i == foundAt + 1) {
                    corporateId = pathcomponents[i];
                } else {
                    inputFilenameBuilder.append('/');
                    inputFilenameBuilder.append(pathcomponents[i]);
                }
            }
        } else {
            // if no corporateid field detected, pass untransformed string
            inputFilenameBuilder.append(s);
        }
        String inputFilename = inputFilenameBuilder.toString();
        InputStream result;
        TemplateV1 templateV1 = simpleTemplateService.findByPathAndCorporateId(inputFilename, corporateId);
        if (templateV1 == null) {
            // try finding default variant
            templateV1 = simpleTemplateService.findByPathAndCorporateId(inputFilename, StringUtils.EMPTY);
            if (templateV1 == null) {
                // Read template from disk ...
                //  String filename = inputFilename.replace(".vm", "");
                //  result = new ByteArrayInputStream(getFile(filename).getBytes(StandardCharsets.UTF_8));
                templateV1 = new TemplateV1();
                try {
                    templateV1.setContent(IOUtils.toString(classpathResourceLoader.getResourceStream(inputFilename.toLowerCase()), "UTF-8"));
                } catch (IOException e) {
                    // error reading template from classpath
                    templateV1.setContent("TEMPLATE " + s + "NOT FOUND!!");
                    LOGGER.error("TEMPLATE NOT FOUND ", e);
                }
            }
        }
        // TODO: CONVERT SPECIAL CHARS TO ENTITIES
        //  String contentEscaped = HtmlUtil.escapeHtmlTextOnly(template.getContent());
        result = new ByteArrayInputStream(templateV1.getContent().getBytes(StandardCharsets.UTF_8));
        return result;
    }

    /**
     * Is source modified.
     *
     * @param resource the resource
     * @return the boolean
     */
    @Override
    public boolean isSourceModified(Resource resource) {

        return true;
    }

    /**
     * Gets last modified.
     *
     * @param resource the resource
     * @return the last modified
     */
    @Override
    public long getLastModified(Resource resource) {

        return 0;
    }

    /**
     * Is caching on.
     *
     * @return the boolean
     */
    @Override
    public boolean isCachingOn() {

        return false;
    }
}
