package com.froso.ufp.modules.core.health;

import com.froso.ufp.modules.core.health.configuration.*;
import org.springframework.context.annotation.*;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HealthConfiguration.class)
public @interface EnableHealth {
}
