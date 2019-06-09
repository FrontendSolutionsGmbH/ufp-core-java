package com.froso.ufp.modules.optional.userresourcefilter.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.userresourcefilter.controller",
        "com.froso.ufp.modules.optional.userresourcefilter.service"
})
public class UserResourceFilterModuleConfiguration {

}
