package com.froso.ufp.modules.core.roles.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

@Document(collection = UserRole.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_USERMANAGMENT"),
                @ResourceKeyword("MENU_RIGHTS_ROLES"),
        })
)
public class UserRole extends ClientReferenceWithName {

    public static final String TYPE_NAME = "UserRole";

    @UfpPossibleLinkTypes({UserRight.class})
    private Set<DataDocumentLink<UserRight>> capabilities = new HashSet<>();

    public UserRole() {

        super(TYPE_NAME);
    }
    public Set<DataDocumentLink<UserRight>> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Set<DataDocumentLink<UserRight>> capabilities) {
        this.capabilities = capabilities;
    }
}
