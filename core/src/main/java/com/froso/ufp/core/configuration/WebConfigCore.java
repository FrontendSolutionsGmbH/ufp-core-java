/*
 * Copyright (c) 2015. Feuerware, Christian Kleinhuis (ck@digitalgott.de)
 */

package com.froso.ufp.core.configuration;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.multipart.*;
import org.springframework.web.multipart.commons.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * The type Web config rest.
 */
@Configuration
// info: componentscan traverses the whole package tree to find any spring annotated classes

public class WebConfigCore extends WebMvcConfigurerAdapter {

    public static final String TEMPLATE_VAR_APPS = "Apps";
    public static final String RESOURCE_PATH_STATIC = "web";
    /**
     * The constant INDEX_HTML.
     */
    public static final String INDEX_HTML = "index.html";
    /**
     * The constant CONFIG_JSON.
     */
    public static final String CONFIG_JSON = "ufp.json";
    public static final String PROPERTY_UFP_STATIC_RESOURCES_PATH = "ufp.static.resources.path";
    public static final String PROPERTY_UFP_STATIC_RESOURCES_CACHE = "ufp.static.resources.cache";
    private static final String APPLICATION = "application";
    private static final String JSON_EXTENDED = "json+extended";
    private static final String JSON_MINIMAL = "json+minimal";
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfigCore.class);
    @Value("${" + PROPERTY_UFP_STATIC_RESOURCES_PATH + "}")
    private String resourcesExternal;

    @Value("${" + PROPERTY_UFP_STATIC_RESOURCES_CACHE + "}")
    private Integer cacheSeconds;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * this line adds mapping of static resources with urls /static/ to /webapp/static
         */
        LOGGER.info("Configuring UFP Static /web path");
        LOGGER.info("Using /web path: " + resourcesExternal);
        registry.addResourceHandler("/web/**").addResourceLocations("file:" + resourcesExternal + "/..")
                .setCachePeriod(cacheSeconds)
                .resourceChain(true)
                .addResolver(new GzipResourceResolver());

        registry.addResourceHandler("/**").addResourceLocations("file:" + resourcesExternal + "/..")
                .setCachePeriod(cacheSeconds)
                .resourceChain(true)
                .addResolver(new GzipResourceResolver());

    }

    /**
     * Configure available web pathes.
     *
     * @param templateGlobalsService the template globals service
     */
    @Autowired(required = false)
    public void configureAvailableWebPathes(TemplateGlobalsService templateGlobalsService) {

        int count = 0;
        LOGGER.info("Initialising Web Resources " + resourcesExternal);
        LOGGER.info("Initialising Web Resources " + templateGlobalsService);
        if (templateGlobalsService != null) {
            LOGGER.info("Initialising Web Resources templateGlobalsServicenotnull");

            File urlWeblocation = new File(resourcesExternal);

            LOGGER.info("STATIC_RESOURCE_PATH==" + urlWeblocation.toString() + " Existant");

            File[] files = urlWeblocation.listFiles();

            // check if index.html exists in that path
            LOGGER.info("Iterating through directory content");
            if (files != null) {
                for (File directory : files) {

                    LOGGER.info("Iterating through directory content" + directory);

                    if (directory.isDirectory()) {
                        LOGGER.info("Path found " + directory);
                        try {
                            File file = new File(directory + "/" + INDEX_HTML);

                            if (file.exists() && !file.isDirectory()) {
                                LOGGER.info("ABSOLUTE PATH_REGISTER IS THEN " + file.getAbsolutePath());
                                // do something
                                LOGGER.info("Found Resource in Web: " + directory);

                                Map<String, Object> data = new HashMap<>();

                                data.put("url", ":baseUrl/" + RESOURCE_PATH_STATIC + "/" + directory + "/" + INDEX_HTML);
                                // check if a config.xml or config.json exists in the path
                                File fileConfigJson = new File(directory + "/" + CONFIG_JSON);
                                if (fileConfigJson.exists()) {
                                    LOGGER.info("Found JSON Config in path " + directory);

                                    try (FileInputStream inputStream = new FileInputStream(fileConfigJson.getAbsoluteFile())) {

                                        String everything = IOUtils.toString(inputStream);
                                        ObjectMapper mapper = new ObjectMapper();

                                        Map<String, Object> map = mapper.readValue(everything, new TypeReference<Map<String, Object>>() {
                                        });
                                        data.put("config", map);
                                    }

                                }

                                data.put("url", ":baseUrl/" + RESOURCE_PATH_STATIC + directory.toString().replace(urlWeblocation.toString(), "") + "/" + INDEX_HTML);
                                data.put("name", directory);
                                templateGlobalsService.registerTemplateVariable(TEMPLATE_VAR_APPS, directory.toString(), data);
                                count++;
                            }
                        } catch (IOException e) {
                            LOGGER.error("Problem reading available resources : " + e.getMessage(), e);
                        }
                    }
                }
            }

        }

        LOGGER.info("Initialising Web Resources Finished  appCount: " + count);

    }

    /**
     * Config web path.
     *
     * @param service the service
     */
    @Autowired
    public void configWebPath(RequestDefinitionService service) {
        service.registerTokenFreePath("/web");
        service.registerTokenFreePath("/" + UFPConstants.API + "/web");
    }

    /**
     * Configure content negotiation.
     *
     * @param configurer the configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        configurer.defaultContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON + ";" + Charset.forName("UTF8")));
        configurer.mediaType("vm", MediaType.TEXT_HTML);
        configurer.mediaType("jpg", MediaType.IMAGE_JPEG);
        configurer.mediaType("png", MediaType.IMAGE_PNG);
        configurer.mediaType("gif", MediaType.IMAGE_GIF);
        configurer.mediaType("pdf", MediaType.APPLICATION_PDF);
        configurer.mediaType("txt", MediaType.TEXT_PLAIN);
        configurer.mediaType("html", MediaType.TEXT_HTML);
        configurer.mediaType("css", MediaType.valueOf("text/css"));
        configurer.mediaType("min.css", MediaType.valueOf("text/css"));
        configurer.parameterName("mode");
        configurer.mediaType("extended", new MediaType(APPLICATION, JSON_EXTENDED));
        configurer.mediaType("minimal", new MediaType(APPLICATION, JSON_MINIMAL));
        configurer.ignoreAcceptHeader(true);
        configurer.parameterName("mediaType");
        configurer.favorParameter(true);
        configurer.favorPathExtension(true);
    }

    /**
     * Configure message converters.
     *
     * @param converters the converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        LOGGER.trace("Configuring Message Converters");
        converters.add(new BufferedImageHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(new FormHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());

        // Default Include everything
        converters.add(getMappingJackson2HttpMessageConverter(MediaType.APPLICATION_JSON_UTF8, JacksonViews.Full.class,
                true));
        converters.add(getMappingJackson2HttpMessageConverter(new MediaType(APPLICATION, JSON_MINIMAL), JacksonViews
                .Minimal.class, false));
        converters.add(getMappingJackson2HttpMessageConverter(new MediaType(APPLICATION, JSON_EXTENDED), JacksonViews
                .Extended.class, false));

    }

    /**
     * Gets mapping jackson 2 http message converter.
     *
     * @param type                 the type
     * @param view                 the view
     * @param defaultViewInclusion the default view inclusion
     * @return the mapping jackson 2 http message converter
     */
    MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter(MediaType type, final Class view,
                                                                               Boolean defaultViewInclusion) {

        MappingJackson2HttpMessageConverter result = new MappingJackson2HttpMessageConverter();

        List<MediaType> types = new ArrayList<>();
        types.add(type);
        result.setSupportedMediaTypes(types);

        result.setObjectMapper(JacksonMapperConfig.getObjectMapper(view));
        LOGGER.trace("Media Types Accepted:" + result.getSupportedMediaTypes().get(0));
        // result.setJsonPrefix(HMACOutputFilter.SECURITY_PREFIX);
        return result;
    }

    /**
     * Supports FileUploads.
     *
     * @return the multipart resolver
     */
    @Bean
    public MultipartResolver multipartResolver() {

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(Integer.MAX_VALUE);
        return multipartResolver;
    }

    /**
     * Configure api resources.
     *
     * @param templateGlobalsService the template globals service
     */
    @Autowired(required = false)
    public void configureApiResources(TemplateGlobalsService templateGlobalsService) {
        if (templateGlobalsService != null) {
            //      templateGlobalsService.registerTemplateVariable(TemplateGlobalsService.TEMPLATE_VAR_RESOURCES, "REST Api Entry", ":baseUrl/api");
            templateGlobalsService.registerTemplateVariable(TemplateGlobalsService.TEMPLATE_VAR_RESOURCES, "Globals Json", ":baseUrl/api/Globals");
        }

    }

}

