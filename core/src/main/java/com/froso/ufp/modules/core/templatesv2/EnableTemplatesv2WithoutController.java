package com.froso.ufp.modules.core.templatesv2;

import com.froso.ufp.modules.core.templatesv2.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(TemplatesV2ModuleConfigurationWithController.class)
public @interface EnableTemplatesv2WithoutController {
}
