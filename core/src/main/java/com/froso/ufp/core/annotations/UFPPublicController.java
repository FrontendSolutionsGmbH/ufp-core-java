package com.froso.ufp.core.annotations;

import java.lang.annotation.*;

/**
 * The interface Ufp exception annotation.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UFPPublicController {

}
