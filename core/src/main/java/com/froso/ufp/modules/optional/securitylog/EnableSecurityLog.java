package com.froso.ufp.modules.optional.securitylog;

import com.froso.ufp.modules.optional.securitylog.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SecurityLogModuleConfiguration.class)
public @interface EnableSecurityLog {
}
