package com.froso.ufp.modules.optional.theme.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.theme.controller",
        "com.froso.ufp.modules.optional.theme.service"
})
public class ThemeModuleConfiguration {

}
