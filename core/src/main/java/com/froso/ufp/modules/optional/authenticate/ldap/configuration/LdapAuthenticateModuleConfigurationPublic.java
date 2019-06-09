package com.froso.ufp.modules.optional.authenticate.ldap.configuration;

import com.froso.ufp.modules.optional.authenticate.ldap.service.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.authenticate.ldap.controller.auth"
}
)
public class LdapAuthenticateModuleConfigurationPublic {

    @Bean
    public LdapAuthenticateService getLdapAuthenticateService() {

        // enter global properties here exposed in the globals ufp endpoint

        return new LdapAuthenticateService();


    }
}
