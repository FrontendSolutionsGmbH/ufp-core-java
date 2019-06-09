package com.froso.ufp.modules.optional.facebookbot.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.bot.*;
import java.util.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("FACEBOOK")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("welcome"),
                        @ResourceVisibleColumn("name")
                }))

)
public class FacebookBotConfiguration extends ClientReference {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "FacebookBotConfiguration";


    /**
     * Constructor Simple app device.
     */
// Constructor
    public FacebookBotConfiguration() {

        super(TYPE_NAME);
    }
}
