package com.froso.ufp.modules.core.events;

import com.froso.ufp.modules.core.events.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EventModuleConfiguration.class)
public @interface EnableEvents {
}
