package com.froso.ufp.modules.optional.sms.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created with IntelliJ IDEA. CoreUser: ck Date: 24.03.14 Time: 13:36 To change this template use File | Settings | File
 * Templates.
 */

@Document(collection = QueueSms.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("SMS"),
                @ResourceKeyword("MENU_COMMUNICATION"),
                @ResourceKeyword("MENU_SMS")
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("_select"),
                        @ResourceVisibleColumn("_edit"),
                        @ResourceVisibleColumn("info.status"),
                        @ResourceVisibleColumn("messageData.text"),
                        @ResourceVisibleColumn("messageData.phoneNumber"),
                        @ResourceVisibleColumn("result.status"),
                        @ResourceVisibleColumn("result.statusMessage"),
                        @ResourceVisibleColumn("result.success")
                }))

)
public class QueueSms extends LowLevelMessageBase<SMSData, SMSProviderResult> {
    public static final String TYPE_NAME = "QueueSms";
    // if not set via user, direct phone number entry ...


    /**
     * Constructor Simple email.
     */
    // Constructor
    public QueueSms() {


        super(TYPE_NAME, new SMSData());
    }

}

