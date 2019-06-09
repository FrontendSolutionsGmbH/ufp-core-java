package com.froso.ufp.modules.optional.textbot.yesno2;

import com.froso.ufp.modules.optional.textbot.yesno2.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(YesNoTextBotConfiguration.class)
public @interface EnableYesNoTextBot {
}
