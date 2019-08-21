package com.froso.ufp.modules.optional.authenticate.authenticatesms;

import com.froso.ufp.modules.optional.authenticate.authenticatesms.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented

@Import(SMSAuthenticateModuleConfiguration.class)
public @interface EnableSMSAuthenticate {
}
