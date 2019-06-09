package com.froso.ufp.modules.optional.media;

import com.froso.ufp.modules.optional.media.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MediaConfiguration.class)
public @interface EnableMedia {


}
