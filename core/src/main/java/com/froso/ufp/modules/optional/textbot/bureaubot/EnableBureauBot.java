package com.froso.ufp.modules.optional.textbot.bureaubot;

import com.froso.ufp.modules.optional.textbot.bureaubot.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SimpleBureauBotModuleConfiguration.class)
public @interface EnableBureauBot {
}
