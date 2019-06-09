package com.froso.ufp.modules.optional.textbot.extended;

import com.froso.ufp.modules.optional.textbot.extended.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ExtendedTextBotConfiguration.class)
public @interface EnableExtendedTextBot {
}
