package com.froso.ufp.modules.optional.facebookbot.annotations;

import java.lang.annotation.*;

/**
 * The interface Ufp exception annotation.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TextBotMethodAnnotation {
    /**
     * Exception id string.
     *
     * @return the string
     */
    String regex();

    String description() default "Description";

    String name() default "Name";

    String command() default "commandName";

    boolean hidden() default false;


}
