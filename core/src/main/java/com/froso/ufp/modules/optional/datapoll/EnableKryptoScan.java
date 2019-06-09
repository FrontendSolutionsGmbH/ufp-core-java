package com.froso.ufp.modules.optional.datapoll;

import com.froso.ufp.modules.optional.datapoll.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented

@Import(DataPollModuleConfiguration.class)

public @interface EnableKryptoScan {
}
