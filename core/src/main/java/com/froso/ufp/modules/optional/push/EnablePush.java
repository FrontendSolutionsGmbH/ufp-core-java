package com.froso.ufp.modules.optional.push;

import com.froso.ufp.modules.optional.push.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(PushConfiguration.class)
public @interface EnablePush {
}
