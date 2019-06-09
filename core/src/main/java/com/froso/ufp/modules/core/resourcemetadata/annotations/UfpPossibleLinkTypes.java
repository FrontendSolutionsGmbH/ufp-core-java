package com.froso.ufp.modules.core.resourcemetadata.annotations;

import java.lang.annotation.*;

/**
 * The interface Ufp exception annotation.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UfpPossibleLinkTypes {

    //   ResourceVisibleColumns defaultVisibleColumns() default @ResourceVisibleColumns;

    Class[] value() default {};

}
