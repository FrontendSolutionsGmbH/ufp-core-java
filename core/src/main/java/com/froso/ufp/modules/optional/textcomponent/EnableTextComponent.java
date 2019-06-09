package com.froso.ufp.modules.optional.textcomponent;

import com.froso.ufp.modules.optional.textcomponent.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(TextComponentModuleConfiguration.class)
public @interface EnableTextComponent {
}
