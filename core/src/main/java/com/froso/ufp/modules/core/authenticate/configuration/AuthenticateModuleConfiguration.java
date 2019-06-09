package com.froso.ufp.modules.core.authenticate.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.authenticate.controller",
        "com.froso.ufp.modules.core.authenticate.service"
})
public class AuthenticateModuleConfiguration {

}
