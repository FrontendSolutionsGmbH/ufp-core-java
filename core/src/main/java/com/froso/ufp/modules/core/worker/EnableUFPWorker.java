package com.froso.ufp.modules.core.worker;

import com.froso.ufp.modules.core.worker.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WorkerModuleConfiguration.class)
public @interface EnableUFPWorker {
}
