package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Document(collection = AuthenticateEmailPassword.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("USERMANAGMENT"),
                @ResourceKeyword("AUTHENTICATION"),
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_AUTHENTICATION")
        })


)
public class AuthenticateEmailPassword extends AbstractAuthentication<EmailPasswordAuthenticateData> implements Authentication<EmailPasswordAuthenticateData> {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "AuthenticateEmailPassword";
    private static final long serialVersionUID = 4416650841174724186L;

    /**
     * Instantiates a new UsernamePassword authenticate model.
     */
    public AuthenticateEmailPassword() {

        super(TYPE_NAME, new EmailPasswordAuthenticateData());
    }

}
