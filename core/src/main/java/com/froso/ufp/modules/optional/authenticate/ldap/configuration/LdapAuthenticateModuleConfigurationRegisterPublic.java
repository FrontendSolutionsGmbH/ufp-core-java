package com.froso.ufp.modules.optional.authenticate.ldap.configuration;

import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.ldap.controller.register"
        })
public class LdapAuthenticateModuleConfigurationRegisterPublic {


}
