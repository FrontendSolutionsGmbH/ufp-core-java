package com.froso.ufp.modules.core.authenticate;

import com.froso.ufp.modules.core.authenticate.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AuthenticateModuleConfiguration.class)
public @interface EnableUFPAuthentication {
}
