package com.froso.ufp.modules.optional.textbot.yesno2.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.textbot.yesno2.controller",
        "com.froso.ufp.modules.optional.textbot.yesno2.actions",
        "com.froso.ufp.modules.optional.textbot.yesno2.service"
})
public class YesNoTextBotConfiguration {

}
