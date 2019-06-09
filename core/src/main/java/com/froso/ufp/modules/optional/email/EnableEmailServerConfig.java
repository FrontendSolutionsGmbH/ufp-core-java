package com.froso.ufp.modules.optional.email;

import com.froso.ufp.modules.optional.email.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EmailServerConfigModuleConfiguration.class)
public @interface EnableEmailServerConfig {
}
