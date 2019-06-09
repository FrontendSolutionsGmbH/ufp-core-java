package com.froso.ufp.modules.optional.comment.configuration;


import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.comment.controller",
        "com.froso.ufp.modules.optional.comment.service"
})
public class CommentModuleConfiguration {

}
