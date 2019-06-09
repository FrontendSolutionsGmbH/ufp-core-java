package com.froso.ufp.modules.core.roles.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Document(collection = RoleDefinition.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_RIGHTS_ROLES"),
        })
)
public class RoleDefinition extends ClientReferenceWithName {

    public static final String TYPE_NAME = "RoleDefinition";

    @UfpPossibleLinkTypes({RoleCapability.class})
    private Set<DataDocumentLink<RoleCapability>> capabilities = new HashSet<>();

    public RoleDefinition() {

        super(TYPE_NAME);
    }
    public Set<DataDocumentLink<RoleCapability>> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<DataDocumentLink<RoleCapability>> capabilities) {
        this.capabilities = capabilities;
    }
}
