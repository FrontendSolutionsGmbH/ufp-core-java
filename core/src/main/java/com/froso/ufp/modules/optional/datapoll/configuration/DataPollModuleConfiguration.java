package com.froso.ufp.modules.optional.datapoll.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.datapoll.controller",
        "com.froso.ufp.modules.optional.datapoll.service"
})
public class DataPollModuleConfiguration {

}
