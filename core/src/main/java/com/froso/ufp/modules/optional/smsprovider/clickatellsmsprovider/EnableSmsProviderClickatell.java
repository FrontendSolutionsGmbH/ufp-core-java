package com.froso.ufp.modules.optional.smsprovider.clickatellsmsprovider;

import com.froso.ufp.modules.optional.smsprovider.clickatellsmsprovider.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ClickatellSmsProviderModuleConfiguration.class)
public @interface EnableSmsProviderClickatell {
}
