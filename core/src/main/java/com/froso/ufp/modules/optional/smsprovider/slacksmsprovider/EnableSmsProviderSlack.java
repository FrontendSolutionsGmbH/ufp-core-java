package com.froso.ufp.modules.optional.smsprovider.slacksmsprovider;

import com.froso.ufp.modules.optional.smsprovider.slacksmsprovider.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SlackSmsProviderModuleConfiguration.class)
public @interface EnableSmsProviderSlack {
}
