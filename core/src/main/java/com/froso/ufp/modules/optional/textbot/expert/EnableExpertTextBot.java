package com.froso.ufp.modules.optional.textbot.expert;

import com.froso.ufp.modules.optional.textbot.expert.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ExpertTextBotConfiguration.class)
public @interface EnableExpertTextBot {
}
