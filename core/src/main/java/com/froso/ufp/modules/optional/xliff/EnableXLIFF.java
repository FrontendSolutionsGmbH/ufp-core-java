package com.froso.ufp.modules.optional.xliff;

import com.froso.ufp.modules.optional.xliff.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(XLIFFConfiguration.class)
public @interface EnableXLIFF {
}
