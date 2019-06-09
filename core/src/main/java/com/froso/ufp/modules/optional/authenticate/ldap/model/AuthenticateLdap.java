package com.froso.ufp.modules.optional.authenticate.ldap.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Document(collection = AuthenticateLdap.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("USERMANAGMENT"),
                @ResourceKeyword("AUTHENTICATION"),
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_AUTHENTICATION")
        })


)
public class AuthenticateLdap extends AbstractAuthentication<LdapAuthenticateDataBase> implements Authentication<LdapAuthenticateDataBase> {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "AuthenticateLdap";

    /**
     * Instantiates a new UsernamePassword authenticate model.
     */
    public AuthenticateLdap() {

        super(TYPE_NAME, new LdapAuthenticateDataBase());
    }

}
