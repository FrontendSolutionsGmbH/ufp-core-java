package com.froso.ufp.modules.optional.chat;

import com.froso.ufp.modules.optional.chat.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ChatModuleConfiguration.class)
public @interface EnableChat {
}
