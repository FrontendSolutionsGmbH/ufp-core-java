package com.froso.ufp.modules.core.resourcemetadata.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceFilterSortValue {

    String value();

    SortMethod direction() default SortMethod.ASC;

}
