package com.froso.ufp.modules.optional.textbot.bureaubot.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.textbot.bureaubot.controller",
        "com.froso.ufp.modules.optional.textbot.bureaubot.service"
})
public class SimpleBureauBotModuleConfiguration {

}
