package com.froso.ufp.modules.optional.crossdomain;

import com.froso.ufp.modules.optional.crossdomain.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(CrossdomainModuleConfiguration.class)
public @interface EnableCrossdomain {
}
