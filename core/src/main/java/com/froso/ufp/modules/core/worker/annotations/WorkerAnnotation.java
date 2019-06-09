package com.froso.ufp.modules.core.worker.annotations;

import java.lang.annotation.*;

/**
 * The interface Ufp exception annotation.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WorkerAnnotation {
    /**
     * Exception id string.
     *
     * @return the string
     */

    String description() default "Description";

    String name() default "Name";

    boolean enabled() default true;


    int intervalSeconds() default 30;


}
