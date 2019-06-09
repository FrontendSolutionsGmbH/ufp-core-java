package com.froso.ufp.modules.core.session;

import com.froso.ufp.modules.core.session.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SessionModuleConfiguration.class)
public @interface EnableSession {
}
