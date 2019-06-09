package com.froso.ufp.modules.optional.authenticate.ldap;

import com.froso.ufp.modules.optional.authenticate.ldap.configuration.*;
import java.lang.annotation.*;
import org.springframework.context.annotation.*;

/**
 * The interface Enable data test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({LdapAuthenticateModuleConfigurationPublic.class})
public @interface EnableLdapAuthenticatePublic {
}
