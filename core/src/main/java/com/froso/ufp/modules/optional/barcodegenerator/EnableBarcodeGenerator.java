package com.froso.ufp.modules.optional.barcodegenerator;

import com.froso.ufp.modules.optional.barcodegenerator.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(BarcodeGeneratorConfiguration.class)
public @interface EnableBarcodeGenerator {
}
