package com.froso.ufp.modules.optional.ftp;

import com.froso.ufp.modules.optional.ftp.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(FTPModuleConfiguration.class)
public @interface EnableFTP {
}
