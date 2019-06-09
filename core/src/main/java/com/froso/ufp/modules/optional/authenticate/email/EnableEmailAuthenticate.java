package com.froso.ufp.modules.optional.authenticate.email;

import com.froso.ufp.modules.optional.authenticate.email.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EmailAuthenticateModuleConfiguration.class)
public @interface EnableEmailAuthenticate {
}
