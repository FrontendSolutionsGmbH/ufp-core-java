package com.froso.ufp.modules.optional.authenticate.masterpassword.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = MasterPasswordModel.TYPE_NAME)
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
                        @ResourceVisibleColumn("enabled"),
                        @ResourceVisibleColumn("verified"),
                        @ResourceVisibleColumn("data.password")
                }))

)
public class MasterPasswordModel extends AbstractAuthentication<MasterPasswordAuthenticateData> implements Authentication<MasterPasswordAuthenticateData> {
    public static final String TYPE_NAME = "MasterPassword";

    public MasterPasswordModel() {

        super(TYPE_NAME, new MasterPasswordAuthenticateData());
    }

}
