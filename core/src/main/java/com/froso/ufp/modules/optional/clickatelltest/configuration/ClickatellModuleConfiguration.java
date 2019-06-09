package com.froso.ufp.modules.optional.clickatelltest.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.clickatelltest.controller",
        "com.froso.ufp.modules.optional.clickatelltest.service"
})
public class ClickatellModuleConfiguration {

}
