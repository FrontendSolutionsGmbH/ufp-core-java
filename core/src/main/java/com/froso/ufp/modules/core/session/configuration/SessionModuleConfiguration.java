package com.froso.ufp.modules.core.session.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.session.controller",
        "com.froso.ufp.modules.core.session.service"
})
public class SessionModuleConfiguration {

}
