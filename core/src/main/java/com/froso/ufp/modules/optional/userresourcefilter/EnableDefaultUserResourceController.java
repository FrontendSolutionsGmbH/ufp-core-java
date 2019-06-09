package com.froso.ufp.modules.optional.userresourcefilter;

import com.froso.ufp.modules.optional.userresourcefilter.configuration.*;
import org.springframework.context.annotation.*;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EnableDefaultController.class)
public @interface EnableDefaultUserResourceController {

}
