package com.froso.ufp.modules.optional.email.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.email.service",
        "com.froso.ufp.modules.optional.email.configuration",
        "com.froso.ufp.modules.optional.email.controller"
})
public class EmailServerConfigModuleConfiguration {

    public static final String RECEIVEMAIL_USERNAME = "receivemail.username";
    public static final String RECEIVEMAIL_PASSWORD = "receivemail.password";
    public static final String RECEIVEMAIL_PORT = "receivemail.port";
    public static final String RECEIVEMAIL_HOST = "receivemail.host";
}
