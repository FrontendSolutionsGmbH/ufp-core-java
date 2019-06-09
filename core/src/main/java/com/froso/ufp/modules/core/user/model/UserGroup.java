package com.froso.ufp.modules.core.user.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 23.10.2014.
 */
@Document(collection = UserGroup.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGroup extends AbstractDataDocumentWithName {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "UserGroup";

    /**
     * Constructor Simple user group.
     */
    public UserGroup() {

        super(TYPE_NAME);
    }
}
