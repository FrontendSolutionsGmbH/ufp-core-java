package com.froso.ufp.modules.core.roles.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.roles.controller",
        "com.froso.ufp.modules.core.roles.service"
})
public class RolesModuleConfiguration {

}
