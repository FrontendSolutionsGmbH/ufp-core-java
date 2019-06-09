package com.froso.ufp.modules.core.user;

import com.froso.ufp.modules.core.user.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(UserModuleConfiguration.class)
public @interface EnableUser {
}
