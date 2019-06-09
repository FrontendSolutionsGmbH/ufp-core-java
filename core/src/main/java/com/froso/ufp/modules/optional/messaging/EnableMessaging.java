package com.froso.ufp.modules.optional.messaging;

import com.froso.ufp.modules.optional.messaging.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented

@Import(EmailConfiguration.class)

public @interface EnableMessaging {
}
