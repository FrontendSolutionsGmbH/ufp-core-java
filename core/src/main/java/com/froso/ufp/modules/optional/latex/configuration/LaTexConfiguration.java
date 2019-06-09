package com.froso.ufp.modules.optional.latex.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.latex.controller",
        "com.froso.ufp.modules.optional.latex.service"
})
public class LaTexConfiguration {

}
