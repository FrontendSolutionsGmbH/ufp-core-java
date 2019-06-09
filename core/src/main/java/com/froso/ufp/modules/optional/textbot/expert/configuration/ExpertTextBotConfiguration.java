package com.froso.ufp.modules.optional.textbot.expert.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.textbot.expert.controller",
        "com.froso.ufp.modules.optional.textbot.expert.service"
})
public class ExpertTextBotConfiguration {

}
