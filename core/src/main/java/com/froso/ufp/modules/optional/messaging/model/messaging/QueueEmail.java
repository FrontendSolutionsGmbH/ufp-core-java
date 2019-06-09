package com.froso.ufp.modules.optional.messaging.model.messaging;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = QueueEmail.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("EMAIL"),
                @ResourceKeyword("MENU_COMMUNICATION"),
                @ResourceKeyword("MENU_EMAIL")
        }),

        views = @ResourceViewsAnnotation({
                @ResourceViewAnnotation(
                        name = "WAITING_TO_SEND Emails",
                        keywords =
                        @ResourceKeywords({
                                @ResourceKeyword("COMMUNICATION"),
                                @ResourceKeyword("EMAIL"),
                                @ResourceKeyword("MENU_COMMUNICATION"),
                                @ResourceKeyword("MENU_EMAIL")
                        }),
                        filter = {
                                @ResourceFilterKeyValue(key = "status", value = "WAITING_TO_SEND")
                        },
                        sort = @ResourceFilterSortValues(
                                @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                        ),
                        visibleColumns = @ResourceVisibleColumns({
                                @ResourceVisibleColumn("messageData.subject"),
                                @ResourceVisibleColumn("status")
                        })
                ),

                @ResourceViewAnnotation(
                        name = "IDLE Emails",
                        keywords =
                        @ResourceKeywords({
                                @ResourceKeyword("COMMUNICATION"),
                                @ResourceKeyword("EMAIL"),
                                @ResourceKeyword("MENU_COMMUNICATION"),
                                @ResourceKeyword("MENU_EMAIL")
                        }),
                        filter = {
                                @ResourceFilterKeyValue(key = "status", value = "IDLE")
                        },
                        sort = @ResourceFilterSortValues(
                                @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                        ),
                        visibleColumns = @ResourceVisibleColumns({
                                @ResourceVisibleColumn("messageData.subject"),
                                @ResourceVisibleColumn("status"),
                        }))

        }
        )

)

public class QueueEmail extends MessageBase<EmailData> {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "QueueEmail";
    private static final long serialVersionUID = 4019766085302583907L;

    /**
     * Constructor Simple email.
     */
    public QueueEmail() {

        super(TYPE_NAME, new EmailData());
    }

}
