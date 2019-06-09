package com.froso.ufp.modules.optional.sms;

import com.froso.ufp.modules.optional.sms.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SMSModuleConfiguration.class)
public @interface EnableSMS {
}
