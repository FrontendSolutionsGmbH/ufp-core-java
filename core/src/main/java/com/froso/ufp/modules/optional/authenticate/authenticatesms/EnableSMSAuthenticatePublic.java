package com.froso.ufp.modules.optional.authenticate.authenticatesms;

import com.froso.ufp.modules.optional.authenticate.authenticatesms.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({SMSAuthenticateModuleConfigurationPublic.class, SMSAuthenticateModuleConfiguration.class})
public @interface EnableSMSAuthenticatePublic {
}
