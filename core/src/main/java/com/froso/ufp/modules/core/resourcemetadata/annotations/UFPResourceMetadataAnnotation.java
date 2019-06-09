package com.froso.ufp.modules.core.resourcemetadata.annotations;

import java.lang.annotation.*;

/**
 * The interface Ufp exception annotation.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UFPResourceMetadataAnnotation {

    //   ResourceVisibleColumns defaultVisibleColumns() default @ResourceVisibleColumns;

    /**
     * Views resource views annotation.
     *
     * @return the resource views annotation
     */
    ResourceViewsAnnotation views() default @ResourceViewsAnnotation;

    /**
     * Icon string.
     *
     * @return the string
     */
    String icon() default "icon";

    /**
     * Default view resource view annotation.
     *
     * @return the resource view annotation
     */
    @Deprecated
    ResourceViewAnnotation defaultView() default @ResourceViewAnnotation;

    /**
     * Keywords resource keywords.
     *
     * @return the resource keywords
     */
    ResourceKeywords keywords() default @ResourceKeywords;

}
