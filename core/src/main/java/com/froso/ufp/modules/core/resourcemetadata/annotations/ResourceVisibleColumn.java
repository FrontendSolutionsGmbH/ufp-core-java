package com.froso.ufp.modules.core.resourcemetadata.annotations;

import java.lang.annotation.*;

/**
 * The interface Ufp exception annotation.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceVisibleColumn {

    /**
     * Value string.
     *
     * @return the string
     */
    String value();

    /**
     * Tags string [ ].
     *
     * @return the string [ ]
     */
    String[] tags() default {};
}
