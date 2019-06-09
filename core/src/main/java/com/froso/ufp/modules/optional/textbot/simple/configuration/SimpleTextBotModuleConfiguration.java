package com.froso.ufp.modules.optional.textbot.simple.configuration;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.textbot.simple.model.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.textbot.simple.controller",
        "com.froso.ufp.modules.optional.textbot.simple.service"
})
public class SimpleTextBotModuleConfiguration {

}
