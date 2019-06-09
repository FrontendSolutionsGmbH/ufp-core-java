package com.froso.ufp.modules.optional.email.model;

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


    /**
     * Gets java mail sender.
     *
     * @return the java mail sender
     */


}
