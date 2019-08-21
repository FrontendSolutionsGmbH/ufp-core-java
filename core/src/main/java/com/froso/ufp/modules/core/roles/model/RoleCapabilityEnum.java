package com.froso.ufp.modules.core.roles.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Document(collection = RoleCapability.TYPE_NAME)

@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_RIGHTS_ROLES"),
        })
)


public class RoleCapability extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "RoleCapability";


    public RoleCapability() {

        super(TYPE_NAME);
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
