/**
 * configuration of the core services, setting up the packages to scan
 */

package com.froso.ufp.core.configuration;

import com.froso.ufp.core.*;
import com.froso.ufp.core.response.filter.*;
import com.froso.ufp.modules.core.applicationproperty.*;
import com.froso.ufp.modules.core.client.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.health.*;
import com.froso.ufp.modules.core.register.*;
import com.froso.ufp.modules.core.requestlogging.*;
import com.froso.ufp.modules.core.session.*;
import com.froso.ufp.modules.core.user.*;
import com.froso.ufp.modules.core.worker.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.hateoas.config.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.validation.*;
import org.springframework.validation.beanvalidation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)

@EnableSession
@EnableAsync
@EnableScheduling
@EnableUFPRegistration
@EnableApplicationProperty
@EnableRequestLogging
@EnableHealth
//@EnableResourceMetadata
//@EnableSpringConfigured
//@EnableLoadTimeWeaving
@EnableUFPWorker
// @EnableRoles
//@EnableEmailServerConfig
@EnableClient
@EnableUser
@ComponentScan(basePackages = {
        "com.froso.ufp.core.service",
        "com.froso.ufp.core.controller",
        "com.froso.ufp.core.service.operations",
        "com.froso.ufp.core.domain.documents.simple",
        "com.froso.ufp.core.eventhandler",
        "com.froso.ufp.core.response",
        "com.froso.ufp.core.configuration"
})

public class CoreServiceConfig {

    /**
     * Gets smart validator.
     *
     * @return the smart validator
     */
    @Bean
    SmartValidator getSmartValidator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Task executor executor.  needed for spring 4.2
     *
     * @return the executor
     */

    /**
     * Configure bean.
     *
     * @param globalsService the globals service
     */
    @Autowired(required = false)
    public void configureBean(GlobalsService globalsService) {

        if (globalsService != null) {
            globalsService.addProperty("sessionDuration", AbstractTokenTranslatorFilter.PROPERTY_SECURITY_USER_VALIDDURATION);
            globalsService.addProperty("applicationName", UFPConstants.PROPERTY_APPLICATION_NAME);
            globalsService.addProperty("applicationVersion", UFPConstants.PROPERTY_APPLICATION_VERSION);
            globalsService.addProperty("applicationBuildTime", UFPConstants.PROPERTY_APPLICATION_BUILDTIME);
            globalsService.addProperty("applicationBuild", UFPConstants.PROPERTY_APPLICATION_BUILD);
        }
    }

}
