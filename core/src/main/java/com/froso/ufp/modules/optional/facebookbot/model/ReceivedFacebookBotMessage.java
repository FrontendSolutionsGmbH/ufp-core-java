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
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("FACEBOOK"),
                @ResourceKeyword("MENU_COMMUNICATION"),
                @ResourceKeyword("MENU_FACEBOOK")
        }),
        defaultView = @ResourceViewAnnotation(

                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("metaData.creationTimestamp"),
                        @ResourceVisibleColumn("status"),
                        @ResourceVisibleColumn("receivedText"),
                        @ResourceVisibleColumn("responseTextSend")
                }))

)
public class ReceivedFacebookBotMessage extends ClientReference {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "ReceivedFacebookBotMessage";
    private FacebookBotMessageStatus status = FacebookBotMessageStatus.NEW;
    private String creator;
    private Map<String, Object> receivedData;
    private String responseTextSend;
    private CallbackData<PageData> dataMessage;
    private String receivedText;
    private Map<String, Object> sendToFacebookData = new HashMap<>();

    /**
     * Constructor Simple app device.
     */
// Constructor
    public ReceivedFacebookBotMessage() {


        super(TYPE_NAME);

    }

    /**
     * Gets received text.
     *
     * @return the received text
     */
    public String getReceivedText() {
        return receivedText;
    }

    /**
     * Sets received text.
     *
     * @param receivedTex the received tex
     */
    public void setReceivedText(String receivedTex) {
        this.receivedText = receivedTex;
    }

    /**
     * Gets data message.
     *
     * @return the data message
     */
    public CallbackData<PageData> getDataMessage() {
        return dataMessage;
    }

    /**
     * Sets data message.
     *
     * @param dataMessage the data message
     */
    public void setDataMessage(CallbackData<PageData> dataMessage) {
        this.dataMessage = dataMessage;
    }

    /**
     * Gets send to facebook data.
     *
     * @return the send to facebook data
     */
    public Map<String, Object> getSendToFacebookData() {
        return sendToFacebookData;
    }

    /**
     * Sets send to facebook data.
     *
     * @param sendToFacebookData the send to facebook data
     */
    public void setSendToFacebookData(Map<String, Object> sendToFacebookData) {
        this.sendToFacebookData = sendToFacebookData;
    }

    /**
     * Gets received data.
     *
     * @return the received data
     */
    public Map<String, Object> getReceivedData() {
        return receivedData;
    }

    /**
     * Sets received data.
     *
     * @param receivedData the received data
     */
    public void setReceivedData(Map<String, Object> receivedData) {
        this.receivedData = receivedData;
    }

    /**
     * Gets response text send.
     *
     * @return the response text send
     */
    public String getResponseTextSend() {
        return responseTextSend;
    }

    /**
     * Sets response text send.
     *
     * @param responseTextSend the response text send
     */
    public void setResponseTextSend(String responseTextSend) {
        this.responseTextSend = responseTextSend;
    }

    /**
     * Gets creator.
     *
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Sets creator.
     *
     * @param creator the creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }


    /**
     * Gets status.
     *
     * @return the status
     */
    public FacebookBotMessageStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(FacebookBotMessageStatus status) {
        this.status = status;
    }


    /**
     * Gets data.
     *
     * @return the data
     */
    public CallbackData<PageData> getData() {
        return dataMessage;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(CallbackData<PageData> data) {
        this.dataMessage = data;
    }
}
