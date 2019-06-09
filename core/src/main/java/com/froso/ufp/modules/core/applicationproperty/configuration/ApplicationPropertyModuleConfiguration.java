package com.froso.ufp.modules.core.applicationproperty.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.applicationproperty.controller",
        "com.froso.ufp.modules.core.applicationproperty.service",
        "com.froso.ufp.modules.core.applicationproperty.configuration"
})
public class ApplicationPropertyModuleConfiguration {

}
