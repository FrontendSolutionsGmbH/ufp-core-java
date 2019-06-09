package com.froso.ufp.modules.optional.latex;

import com.froso.ufp.modules.optional.latex.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(LaTexConfiguration.class)
public @interface EnableLaTeX {
}
