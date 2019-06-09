package com.froso.ufp.modules.optional.authenticate.masterpassword;

import com.froso.ufp.modules.optional.authenticate.masterpassword.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MasterPasswordModuleConfiguration.class)
public @interface EnableMasterPassword {
}
