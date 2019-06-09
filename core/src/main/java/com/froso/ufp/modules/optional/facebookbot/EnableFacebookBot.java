package com.froso.ufp.modules.optional.facebookbot;

import com.froso.ufp.modules.optional.facebookbot.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FacebookBotModuleConfiguration.class)
public @interface EnableFacebookBot {
}
