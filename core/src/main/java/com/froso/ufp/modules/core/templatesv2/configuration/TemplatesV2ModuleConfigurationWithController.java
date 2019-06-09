package com.froso.ufp.modules.core.templatesv2.configuration;

import com.froso.ufp.core.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.templatesv2.configuration",
        "com.froso.ufp.modules.core.templatesv2.service",
        "com.froso.ufp.modules.core.templatesv2.controller",
})
public class TemplatesV2ModuleConfigurationWithController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplatesV2ModuleConfigurationWithController.class);

    @Autowired
    public void configWebPath(RequestDefinitionService service) {
        service.registerTokenFreePath("/" + UFPConstants.API + "/" + FileTemplate.TYPE_NAME);
        service.registerTokenFreePath("/home");
    }
    /**
     * Gets velocity configurer.
     *
     * @param simpleTemplateService the simple template service
     * @return the velocity configurer
     */
    /*
    @Bean
    @Autowired
    public VelocityConfigurer getVelocityConfigurer(SimpleTemplateService simpleTemplateService) {

        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        // Important: the velocity view resolver uses this configurer to set up the velocity engine, hence we need
        // the database loading it is wired here!
        velocityConfigurer.setVelocityEngine(getVelocityEngine(simpleTemplateService));
        return velocityConfigurer;
    }
          */

    /**
     * Gets velocity engine.
     *
     * @param simpleTemplateService the simple template service
     * @return the velocity engine
     */

/*    @Autowired
    @Bean
    public VelocityEngine getVelocityEngine(SimpleTemplateService simpleTemplateService) {

        VelocityEngine result = new VelocityEngine();
        ToolManager toolManager = new ToolManager();
        toolManager.setUserCanOverwriteTools(true);
        toolManager.autoConfigure(true);

        // Configure Database Template Loading
      //  result.setProperty(RuntimeConstants.RESOURCE_LOADER, "database");
        //result.setProperty("database.resource.loader.instance", new service.VelocityResourceLoaderMongoDB(simpleTemplateService));
        // Disable Loading of default Macro Library
        result.setProperty(RuntimeConstants.VM_LIBRARY, "");
        result.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
        result.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
     /*
        // use classpath template resource location
        result.setProperty(RuntimeConstants.RESOURCE_LOADER, "database");
        result.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        * /
        try {
            result.init();
        } catch (Exception e) {
            LOGGER.error("Web Init Problem Velocity Engine:" + e.getMessage(), e);
        }
        return result;
    }
    */

}
