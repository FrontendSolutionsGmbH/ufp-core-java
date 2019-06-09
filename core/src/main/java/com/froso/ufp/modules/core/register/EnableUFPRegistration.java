package com.froso.ufp.modules.core.register;

import com.froso.ufp.modules.core.register.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RegisterModuleConfiguration.class)
public @interface EnableUFPRegistration {
}
