package com.froso.ufp.modules.optional.textbot.simple;

import com.froso.ufp.modules.optional.textbot.simple.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SimpleTextBotModuleConfiguration.class)
public @interface EnableSimpleTextBot {
}
