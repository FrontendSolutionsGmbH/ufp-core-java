package com.froso.ufp.modules.optional.websockets.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.websockets.configuration",
        "com.froso.ufp.modules.optional.websockets.controller",
        "com.froso.ufp.modules.optional.websockets.eventhandler",
        "com.froso.ufp.modules.optional.websockets.service"
})
public class WebsocketModuleConfiguration {

}
