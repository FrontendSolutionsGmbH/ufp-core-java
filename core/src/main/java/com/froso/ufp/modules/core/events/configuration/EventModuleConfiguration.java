package com.froso.ufp.modules.core.events.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.events.controller",
        "com.froso.ufp.modules.core.events.service"
})
public class EventModuleConfiguration {

}
