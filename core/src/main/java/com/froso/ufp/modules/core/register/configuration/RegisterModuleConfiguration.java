package com.froso.ufp.modules.core.register.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.register.controller",
        "com.froso.ufp.modules.core.register.service"
})
public class RegisterModuleConfiguration {

}
