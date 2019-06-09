package com.froso.ufp.modules.optional.authenticate.email.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Document(collection = EmailAuthenticateModel.TYPE_NAME)
@CompoundIndexes({
        @CompoundIndex(name = "email-username",
                def = "{'data.email': 1, 'data.userName': 1}")
})
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_AUTHENTICATION"),
                @ResourceKeyword("USERMANAGMENT"),
                @ResourceKeyword("AUTHENTICATION")
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("_select"),
                        @ResourceVisibleColumn("_edit"),
                        @ResourceVisibleColumn("coreUser"),
                        @ResourceVisibleColumn("data.email"),
                        @ResourceVisibleColumn("enabled"),
                        @ResourceVisibleColumn("verified"),
                }))

)
public class EmailAuthenticateModel extends AbstractAuthentication<EmailAuthenticateData> implements Authentication<EmailAuthenticateData> {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "AuthenticateEmail";

    /**
     * Instantiates a new Sms authenticate model.
     */
    public EmailAuthenticateModel() {

        super(TYPE_NAME, new EmailAuthenticateData());
    }

}
