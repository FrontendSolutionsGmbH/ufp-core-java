package com.froso.ufp.modules.optional.authenticate.ldap.configuration;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import com.froso.ufp.modules.core.register.service.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;
import com.froso.ufp.modules.optional.authenticate.ldap.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.ldap.core.*;
import org.springframework.ldap.core.support.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.ldap.controller.base"
        })
public class LdapAuthenticateModuleConfiguration {

    /**
     * The constant PROP_NAME_LDAP_ROOT.
     */
    public static final String PROP_NAME_LDAP_SUFFIX = "ufp.optional.authenticate.Ldap.partitionSuffix";
    /**
     * The constant PROP_NAME_LDAP_PRINCIPAL.
     */
    public static final String PROP_NAME_LDAP_PRINCIPAL = "ufp.optional.authenticate.Ldap.principal";
    /**
     * The constant PROP_NAME_LDAP_PASSWORD.
     */
    public static final String PROP_NAME_LDAP_PASSWORD = "ufp.optional.authenticate.Ldap.password";
    /**
     * The constant PROP_NAME_LDAP_URL.
     */
    public static final String PROP_NAME_LDAP_URL = "ufp.optional.authenticate.Ldap.url";
    public static final String PROP_NAME_LDAP_CLIENT_PASSWORD_ENCODE = "ufp.optional.authenticate.Ldap.passwordEncoding";

    /**
     * The constant LDAP_SUFFIX.
     */
    public static final String LDAP_SUFFIX = "dc=startupzoom,dc=ufp,dc=org";

    /**
     * Ldap context source ldap context source.
     *
     * @param propertyService the property service
     * @return the ldap context source
     */
    @Bean
    @Autowired
    public LdapContextSource ldapContextSource(ApplicationPropertyService propertyService) {
        // enter global properties here exposed in the globals ufp endpoint
        LdapContextSource result = new LdapContextSource();
        String suffix = propertyService.getPropertyValue(PROP_NAME_LDAP_SUFFIX, "dc=startupzoom,dc=ufp,dc=org");
        result.setUserDn(propertyService.getPropertyValue(PROP_NAME_LDAP_PRINCIPAL, "cn=admin," + suffix));
        result.setPassword(propertyService.getPropertyValue(PROP_NAME_LDAP_PASSWORD, "password"));
        result.setUrl(propertyService.getPropertyValue(PROP_NAME_LDAP_URL, "ldap://localhost:389"));
        result.setBase(suffix);
        return result;
    }

    /**
     * Ldap template ldap template.
     *
     * @param source the source
     * @return the ldap template
     */
    @Bean
    @Autowired
    public LdapTemplate ldapTemplate(LdapContextSource source) {

        // enter global properties here exposed in the globals ufp endpoint
        LdapTemplate result = new LdapTemplate();
        result.setContextSource(source);

        return result;
    }

    /**
     * Gets email password authenticate crud service.
     *
     * @return the email password authenticate crud service
     */
    @Bean
    public LdapAuthenticateCRUDService getLdapAuthenticateCRUDService() {

        // enter global properties here exposed in the globals ufp endpoint

        return new LdapAuthenticateCRUDService();

    }

    /**
     * Configure bean.
     *
     * @param requestDefinitionService the request definition service
     */
    @Autowired(required = false)
    public void configureUfpLdap(RequestDefinitionService requestDefinitionService) {
        requestDefinitionService.registerTokenFreePath("/" + LdapConstants.PATH_AUTH);
//        requestDefinitionService.registerTokenFreePath("/" + LdapConstants.PATH_AUTH + "/ResetPw");
//        requestDefinitionService.registerTokenFreePath("/" + LdapConstants.PATH_REGISTER);
        // enter global properties here exposed in the globals ufp endpoint

    }

    /**
     * Gets service.
     *
     * @return the service
     */
    @Bean
    @Autowired
    public LdapRegisterService getLdapRegisterService(RegistrationsService registrationsService) {

        // enter global properties here exposed in the globals ufp endpoint
        return new LdapRegisterService(registrationsService);

    }
}
