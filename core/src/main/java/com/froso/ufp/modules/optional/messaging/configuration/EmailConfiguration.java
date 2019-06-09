package com.froso.ufp.modules.optional.messaging.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.messaging.service",
        "com.froso.ufp.modules.optional.messaging.controller"
})
public class EmailConfiguration {

    public static final String SENDMAIL_USERNAME = "sendmail.username";
    public static final String SENDMAIL_PASSWORD = "sendmail.password";
    public static final String SENDMAIL_PORT = "sendmail.port";
    public static final String SENDMAIL_HOST = "sendmail.host";

    /**
     * Gets java mail sender.
     *
     * @return the java mail sender
     */


}
