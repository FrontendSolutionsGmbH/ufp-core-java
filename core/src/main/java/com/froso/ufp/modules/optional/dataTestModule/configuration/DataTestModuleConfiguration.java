package com.froso.ufp.modules.optional.dataTestModule.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.dataTestModule.controller",
        "com.froso.ufp.modules.optional.dataTestModule.service"
})
public class DataTestModuleConfiguration {

}
