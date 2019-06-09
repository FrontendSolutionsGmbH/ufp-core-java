package com.froso.ufp.modules.optional.authenticate.authenticatefacebook;

import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FacebookModuleConfiguration.class)
public @interface EnableFacebookAuthenticate {
}
