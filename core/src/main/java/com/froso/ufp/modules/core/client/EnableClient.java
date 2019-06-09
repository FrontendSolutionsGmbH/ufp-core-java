package com.froso.ufp.modules.core.client;

import com.froso.ufp.modules.core.client.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ClientConfiguration.class)
public @interface EnableClient {
}
