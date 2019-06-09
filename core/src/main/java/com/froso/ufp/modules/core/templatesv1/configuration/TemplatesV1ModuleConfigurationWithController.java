package com.froso.ufp.modules.core.templatesv1.configuration;

import com.froso.ufp.modules.core.templatesv1.service.*;
import com.froso.ufp.modules.core.velocity.*;
import org.apache.commons.lang3.*;
import org.apache.velocity.app.*;
import org.apache.velocity.runtime.*;
import org.apache.velocity.tools.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.templatesv1.configuration",
        "com.froso.ufp.modules.core.templatesv1.controller",
})
public class TemplatesV1ModuleConfigurationWithController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplatesV1ModuleConfigurationWithController.class);

    /**
     * Gets velocity configurer.
     *
     * @param simpleTemplateService the simple template service
     * @return the velocity configurer
     */
    @Bean
    @Autowired
    public VelocityConfigurer getVelocityConfigurer(SimpleTemplateService simpleTemplateService) {

        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        // Important: the velocity view resolver uses this configurer to set up the velocity engine, hence we need
        // the database loading it is wired here!
        velocityConfigurer.setVelocityEngine(getVelocityEngine(simpleTemplateService));
        return velocityConfigurer;
    }


    /**
     * Gets velocity engine.
     *
     * @param simpleTemplateService the simple template service
     * @return the velocity engine
     */
    @Autowired
    @Bean
    //fixme: MAKE VELOCITY INSTANTIATION ON A SINGLE PLACE!
    public VelocityEngine getVelocityEngine(SimpleTemplateService simpleTemplateService) {

        VelocityEngine result = new VelocityEngine();
        ToolManager toolManager = new ToolManager();
        toolManager.setUserCanOverwriteTools(true);
        toolManager.autoConfigure(true);

        // Configure Database Template Loading
        result.setProperty(RuntimeConstants.RESOURCE_LOADER, "database");
        result.setProperty("database.resource.loader.instance", new VelocityResourceLoaderMongoDB(simpleTemplateService));
        // Disable Loading of default Macro Library
        result.setProperty(RuntimeConstants.VM_LIBRARY, StringUtils.EMPTY);
        result.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        result.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");

        result.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        result.setProperty("runtime.log.logsystem.log4j.category", "velocity");
        result.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
     /*
        // use classpath template resource location
        result.setProperty(RuntimeConstants.RESOURCE_LOADER, "database");
        result.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        */
        try {
            result.init();
        } catch (Exception e) {
            LOGGER.error("Web Init Problem Velocity Engine:" + e.getMessage(), e);
        }
        return result;
    }

}
