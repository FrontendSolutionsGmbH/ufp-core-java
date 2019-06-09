package com.froso.ufp.modules.core.applicationproperty;

import com.froso.ufp.modules.core.applicationproperty.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ApplicationPropertyModuleConfiguration.class)
public @interface EnableApplicationProperty {
}
