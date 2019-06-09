package com.froso.ufp.modules.core.workflow.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.core.workflow.controller",
        "com.froso.ufp.modules.core.workflow.eventhandler",
        "com.froso.ufp.modules.core.workflow.service"
})
public class WorkflowConfiguration {

}
