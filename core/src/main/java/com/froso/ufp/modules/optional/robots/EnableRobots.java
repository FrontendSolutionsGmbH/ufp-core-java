package com.froso.ufp.modules.optional.robots;

import com.froso.ufp.modules.optional.robots.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RobotsModuleConfiguration.class)
public @interface EnableRobots {
}
