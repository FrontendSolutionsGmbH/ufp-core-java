package com.froso.ufp.modules.optional.smsprovider.thetexting;

import com.froso.ufp.modules.optional.smsprovider.thetexting.configuration.*;
import org.springframework.context.annotation.*;

import java.lang.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(TheTextingProviderModuleConfiguration.class)
public @interface EnableSmsProviderTheTexting {
}
