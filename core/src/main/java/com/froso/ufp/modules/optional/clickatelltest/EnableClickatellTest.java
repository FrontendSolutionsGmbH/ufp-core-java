package com.froso.ufp.modules.optional.clickatelltest;

import com.froso.ufp.modules.optional.clickatelltest.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ClickatellModuleConfiguration.class)
public @interface EnableClickatellTest {
}
