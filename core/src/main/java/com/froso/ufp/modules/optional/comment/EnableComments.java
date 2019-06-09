package com.froso.ufp.modules.optional.comment;

import com.froso.ufp.modules.optional.comment.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(CommentModuleConfiguration.class)
public @interface EnableComments {
}
