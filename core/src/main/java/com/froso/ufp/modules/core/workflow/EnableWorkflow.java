package com.froso.ufp.modules.core.workflow;

import com.froso.ufp.modules.core.workflow.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WorkflowConfiguration.class)
public @interface EnableWorkflow {
}
