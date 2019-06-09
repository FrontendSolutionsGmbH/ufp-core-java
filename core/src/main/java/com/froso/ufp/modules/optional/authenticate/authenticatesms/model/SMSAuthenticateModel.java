package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = SMSAuthenticateModel.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("USERMANAGMENT"),
                @ResourceKeyword("AUTHENTICATION"),
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_AUTHENTICATION")
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("_select"),
                        @ResourceVisibleColumn("_edit"),
                        @ResourceVisibleColumn("coreUser"),
                        @ResourceVisibleColumn("data.phoneNumber"),
                        @ResourceVisibleColumn("lowLevelSMSDataDocumentLink"),
                        @ResourceVisibleColumn("enabled"),
                        @ResourceVisibleColumn("verified"),
                }))

)
public class SMSAuthenticateModel extends AbstractAuthentication<SMSAuthenticateData> implements Authentication<SMSAuthenticateData> {
    public static final String TYPE_NAME = "AuthenticateSms";

    public SMSAuthenticateModel() {

        super(TYPE_NAME, new SMSAuthenticateData());
    }

}
