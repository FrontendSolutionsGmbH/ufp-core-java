package com.froso.ufp.modules.core.templatesv1;

import com.froso.ufp.modules.core.templatesv1.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(TemplatesV1ModuleConfigurationWithController.class)
public @interface EnableTemplatesv1WithController {
}
