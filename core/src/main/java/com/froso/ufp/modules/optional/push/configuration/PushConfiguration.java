package com.froso.ufp.modules.optional.push.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.push.service",
        "com.froso.ufp.modules.optional.push.controller"
})
public class PushConfiguration {

}
