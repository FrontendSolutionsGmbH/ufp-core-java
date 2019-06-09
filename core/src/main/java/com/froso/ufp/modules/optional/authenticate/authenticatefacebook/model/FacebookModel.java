package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Document(collection = FacebookModel.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("USERMANAGMENT"),
                @ResourceKeyword("MENU_AUTHENTICATION"),
                @ResourceKeyword("AUTHENTICATION")
        }),
        defaultView = @ResourceViewAnnotation(visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("coreUser"),
                @ResourceVisibleColumn("enabled"),
                @ResourceVisibleColumn("verified"),
        }))

)
public class FacebookModel extends AbstractAuthentication<FacebookData>  {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "AuthenticateFacebook";


    /**
     * Constructor Simple app device.
     */
// Constructor
    public FacebookModel() {
        super(TYPE_NAME, new FacebookData());
    }

}
