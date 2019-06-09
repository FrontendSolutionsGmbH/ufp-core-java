package com.froso.ufp.modules.core.roles;

import com.froso.ufp.modules.core.roles.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RolesModuleConfiguration.class)
public @interface EnableRoles {
}
