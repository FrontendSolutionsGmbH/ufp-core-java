package com.froso.ufp.modules.optional.authenticate.emailpassword;

import com.froso.ufp.modules.optional.authenticate.emailpassword.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented

@Import(EmailPasswordAuthenticateModuleConfiguration.class)
public @interface EnableEmailPasswordAuthenticate {
}
