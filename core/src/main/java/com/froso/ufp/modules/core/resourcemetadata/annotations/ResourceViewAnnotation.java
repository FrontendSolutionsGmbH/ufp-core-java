package com.froso.ufp.modules.core.resourcemetadata.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceViewAnnotation {

    /**
     * Filter resource filter key value [ ].
     *
     * @return the resource filter key value [ ]
     */
    ResourceFilterKeyValue[] filter() default {};

    /**
     * Keywords resource keywords.
     *
     * @return the resource keywords
     */
    ResourceKeywords keywords() default @ResourceKeywords;

    /**
     * Auto refresh enabled boolean.
     *
     * @return the boolean
     */
    boolean autoRefreshEnabled() default false;

    /**
     * Auto refresh interval int.
     *
     * @return the int
     */
    int autoRefreshInterval() default 1000;

    /**
     * Sort resource filter sort values.
     *
     * @return the resource filter sort values
     */
    ResourceFilterSortValues sort() default @ResourceFilterSortValues;

    /**
     * Visible columns resource visible columns.
     *
     * @return the resource visible columns
     */
    ResourceVisibleColumns visibleColumns() default @ResourceVisibleColumns;

    /**
     * View type view type.
     *
     * @return the view type
     */
    ViewType viewType() default ViewType.FILTER;

    /**
     * Name string.
     *
     * @return the string
     */
    String name() default "";

}
