package com.froso.ufp.modules.optional.push.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.push.model.push.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created with IntelliJ IDEA. CoreUser: ck Date: 24.03.14 Time: 13:36 To change this template use File | Settings | File
 * Templates.
 */
@Document(collection = QueuePushMessage.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)


@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("PUSH")
        }),
        defaultView = @ResourceViewAnnotation(sort = @ResourceFilterSortValues(
                @ResourceFilterSortValue(value = "metaData.lastChangedTimestamp", direction = SortMethod.DESC)
        ),

                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("_edit"),
                        @ResourceVisibleColumn("status"),
                        @ResourceVisibleColumn("data.messageTitle"),
                        @ResourceVisibleColumn("data.messageType")

                })),
        views = @ResourceViewsAnnotation({
                @ResourceViewAnnotation(
                        name = "WAITING_TO_SEND Push",

                        filter = {

                                @ResourceFilterKeyValue(key = "status", value = "WAITING_TO_SEND")

                        },
                        visibleColumns = @ResourceVisibleColumns({

                                @ResourceVisibleColumn("id"),
                                @ResourceVisibleColumn("data.messageTitle"),
                                @ResourceVisibleColumn("data.messageType")
                        })
                ),

                @ResourceViewAnnotation(
                        name = "IDLE_PUSH",
                        filter = {
                                @ResourceFilterKeyValue(key = "status", value = "IDLE")
                        },
                        visibleColumns = @ResourceVisibleColumns({

                                @ResourceVisibleColumn("_edit"),
                                @ResourceVisibleColumn("receiverUser"),
                                @ResourceVisibleColumn("data.messageTitle"),
                                @ResourceVisibleColumn("data.messageType"),
                                @ResourceVisibleColumn("status")
                        }))


        }
        )

)

public class QueuePushMessage extends MessageBase<PushData> {


    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "QueuePushMessage";
    private AbstractBasePushMessage data;


    /**
     * Constructor Simple email.
     */
    // Constructor
    public QueuePushMessage() {
        super(TYPE_NAME, new PushData());
    }

    public AbstractBasePushMessage getData() {
        return data;
    }

    public void setData(AbstractBasePushMessage data) {
        this.data = data;
    }

}
