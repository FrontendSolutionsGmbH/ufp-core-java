package com.froso.ufp.modules.core.templatesv2.configuration;

import com.froso.ufp.modules.core.velocity.*;
import com.froso.ufp.modules.optional.ftp.*;
import com.froso.ufp.modules.optional.media.*;
import org.apache.commons.lang3.*;
import org.apache.velocity.app.*;
import org.apache.velocity.runtime.*;
import org.apache.velocity.tools.*;
import org.slf4j.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
//@EnableFTP
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.templatesv2.service"
})
public class TemplatesV2ModuleConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplatesV2ModuleConfiguration.class);

    /**
     * Gets velocity configurer.
     *
     * @return the velocity configurer
     */
    @Bean
    public VelocityConfigurer getVelocityConfigurer() {

        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        return velocityConfigurer;
    }


    /**
     * Gets velocity engine.
     *
     * @return the velocity engine
     */
    @Bean
    public VelocityEngine getVelocityEngine() {

        VelocityEngine result = new VelocityEngine();
        ToolManager toolManager = new ToolManager();
        toolManager.setUserCanOverwriteTools(true);
        toolManager.autoConfigure(true);

        // Configure Database Template Loading
        //       result.setProperty(RuntimeConstants.RESOURCE_LOADER, "database");
        //      result.setProperty("database.resource.loader.instance", new VelocityResourceLoaderMongoDB(simpleTemplateService));
        // Disable Loading of default Macro Library
        result.setProperty(RuntimeConstants.VM_LIBRARY, StringUtils.EMPTY);
        result.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        result.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        //    result.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogSystem");
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
