package com.froso.ufp.modules.optional.authenticate.ldap.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.authenticate.ldap.configuration.*;
import com.froso.ufp.modules.optional.authenticate.ldap.controller.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;
import java.util.*;
import javax.naming.directory.*;
import org.apache.commons.lang3.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.ldap.core.support.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class LdapAuthenticateService extends AbstractAuthenticateService {
    /**
     * The constant PROP_NAME_NONCE_LENGTH.
     * ufp.controller.authenticate.UsernamePassword.autologin=true
     * # for debugging this nonce is valid for all accounts
     * ufp.controller.authenticate.UsernamePassword.devnonce=1234
     * # for debugging this nonce is valid for all accounts
     * ufp.controller.authenticate.UsernamePassword.usedevnonce=true
     */
    public static final String PROP_NAME_UsernamePassword_NONCE_LENGTH = "ufp.optional.authenticate.UsernamePassword.noncelength";

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapAuthenticateService.class);

    /**
     * The Low level UsernamePassword sender service.
     */
    @Autowired
    private LdapAuthenticateCRUDService usernamePasswordAuthenticateCRUDService;

    @Autowired
    private IPropertyService propertyService;

    @Autowired
    private LdapContextSource ldapContextSource;

    /**
     * Instantiates a new UsernamePassword authenticate service.
     */
    public LdapAuthenticateService() {

        super();

    }

    /**
     * Gets nonce.
     *
     * @param nonceValue the nonce value
     * @return the nonce
     */
    public AuthenticateLdap getNonce(String nonceValue) {
        AuthenticateLdap model = usernamePasswordAuthenticateCRUDService.findOneByKeyValue("data.nonceData.nonce", nonceValue);
        return model;
    }

    public List<Authentication> getAuthenticationsForUser(String coreUserId) {


        return usernamePasswordAuthenticateCRUDService.getAllEntriesForCoreUserId(coreUserId);


    }

    private void createCoreUserForUsernamePasswordAuth(AuthenticateLdap UsernamePasswordAuthenticateModel) {
        ICoreUser coreUser = coreUserHelperService.createUser(null);
        UsernamePasswordAuthenticateModel.getCoreUser().setId(coreUser.getId());
        usernamePasswordAuthenticateCRUDService.save(UsernamePasswordAuthenticateModel);
// publish event for associated core user and UsernamePassword auth
        //  this.publisher.publishEvent(new UsernamePasswordEntiteitCreatedEvent(coreUser, UsernamePasswordAuthenticateModel, this));
    }

    private String createNonce() {

        return RandomStringUtils.randomNumeric(propertyService.getPropertyValueInteger(PROP_NAME_UsernamePassword_NONCE_LENGTH));

    }


    private Boolean verifyNonceToModel(AuthenticateLdap model, LdapAuthenticateRequestData data) {


        // check if stored nonce is in valid duration
        throw new LdapAuthenticateException.UsernamePasswordAuthDisabled();


    }

    private TokenData handleFirstTimeNumberRegistered(AuthenticateLdap UsernamePasswordAuthenticateModel, LdapAuthenticateRequestData data) {
        // existant phonenumber, existant coreuser reference

        //  UsernamePasswordAuthenticateModel.getData().setUsername(null);
        UsernamePasswordAuthenticateModel.setVerified(true);
        usernamePasswordAuthenticateCRUDService.save(UsernamePasswordAuthenticateModel);
        return authenticationsService.finalizeAuthorization(UsernamePasswordAuthenticateModel);

    }


    private Boolean authenticateAgainstLiveLDAP(LdapAuthenticateRequestData data) {

        try {
          DirContext context=  ldapContextSource
                    .getContext(
                            "cn=" + data.getUsername() +
                                    ",ou=users," + LdapAuthenticateModuleConfiguration.LDAP_SUFFIX, data.getPassword());

               LOGGER.info("CONTEXTIS"+context.toString());
        } catch (Exception e) {

            LOGGER.error("authenticateAgainstLiveLDAP", e);
            return false;

        }
        return true;

    }

    /**
     * Login or register UsernamePassword authenticate data.
     *
     * @param data the data
     * @return the token data
     */
    public TokenData authenticateUsernamePasswordData(LdapAuthenticateRequestData data) {
        TokenData result = null;
        // check if the phonenumber is already present


        if (authenticateAgainstLiveLDAP(data)) {

            LOGGER.info("Log in LDAP succesful");
            // check if ufp user already existant

            AuthenticateLdap ldapAuthenticateModel = usernamePasswordAuthenticateCRUDService.findOneByKeyValue("data.username", "=" + data.getUsername());
            if (ldapAuthenticateModel == null) {

                // not existant, create ldap link and default user

                ldapAuthenticateModel = usernamePasswordAuthenticateCRUDService.createNewDefault();
                ldapAuthenticateModel.getData().setUsername(data.getUsername());
                String[] elems = data.getUsername().split("\\.");
                ICoreUser coreUser = (ICoreUser) userService.createNewDefault();
                coreUser.setFirstName(elems[0]);
                if (elems.length >= 2) {
                    coreUser.setLastName(elems[1]);
                }
                ldapAuthenticateModel.getCoreUser().setId(coreUser.getId());
                userService.save(coreUser);
                usernamePasswordAuthenticateCRUDService.save(ldapAuthenticateModel);
                return authenticationsService.finalizeAuthorization(ldapAuthenticateModel);


            } else {
                // erxistant, login
                return authenticationsService.finalizeAuthorization(ldapAuthenticateModel);
            }
        }

        return result;

    }

    protected String getAuthenticationName() {
        return LdapConstants.NAME;
    }


}
