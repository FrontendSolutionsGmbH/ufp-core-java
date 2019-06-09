package com.froso.ufp.modules.optional.xliff.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.xliff.controller",
        "com.froso.ufp.modules.optional.xliff.service"
})
public class XLIFFConfiguration {

}
