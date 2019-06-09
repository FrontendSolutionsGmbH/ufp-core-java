package com.froso.ufp.modules.optional.textbot.extended.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.textbot.extended.controller",
        "com.froso.ufp.modules.optional.textbot.extended.service"
})
public class ExtendedTextBotConfiguration {

}
