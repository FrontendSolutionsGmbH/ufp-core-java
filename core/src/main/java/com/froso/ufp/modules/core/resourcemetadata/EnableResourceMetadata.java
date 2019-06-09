package com.froso.ufp.modules.core.resourcemetadata;

import com.froso.ufp.modules.core.resourcemetadata.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ResourceMetadataModuleConfiguration.class)
public @interface EnableResourceMetadata {
}
