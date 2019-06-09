package com.froso.ufp.modules.optional.websockets;

import com.froso.ufp.modules.optional.websockets.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WebsocketModuleConfiguration.class)
public @interface UFPEnableWebsockets {
}
