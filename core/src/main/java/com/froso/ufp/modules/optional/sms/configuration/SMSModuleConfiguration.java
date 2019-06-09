package com.froso.ufp.modules.optional.sms.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.sms.service",
        "com.froso.ufp.modules.optional.sms.configuration",
        "com.froso.ufp.modules.optional.sms.controller",
        "com.froso.ufp.modules.optional.sms.model.sms"
})
public class SMSModuleConfiguration {

}
