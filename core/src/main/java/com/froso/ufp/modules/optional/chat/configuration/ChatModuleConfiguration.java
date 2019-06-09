package com.froso.ufp.modules.optional.chat.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.chat.controller",
        "com.froso.ufp.modules.optional.chat.service"
})
public class ChatModuleConfiguration {

}
