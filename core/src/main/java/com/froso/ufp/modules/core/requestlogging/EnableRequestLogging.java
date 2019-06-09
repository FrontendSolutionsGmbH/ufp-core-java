package com.froso.ufp.modules.core.requestlogging;

import com.froso.ufp.modules.core.requestlogging.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RequestLoggingConfiguration.class)
public @interface EnableRequestLogging {
}
