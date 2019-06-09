package com.froso.ufp.modules.optional.theme;

import com.froso.ufp.modules.optional.theme.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ThemeModuleConfiguration.class)
public @interface EnableTheme {
}
