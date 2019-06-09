package com.froso.ufp.modules.core.globals;

import com.froso.ufp.modules.core.globals.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(GlobalsConfiguration.class)
public @interface EnableGlobals {
}
