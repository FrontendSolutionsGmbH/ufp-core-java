package com.froso.ufp.modules.optional.dataTestModule;

import com.froso.ufp.modules.optional.dataTestModule.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DataTestModuleConfiguration.class)
public @interface EnableDataTest {
}
