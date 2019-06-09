package com.froso.ufp.modules.optional.messaging.model;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import java.util.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("EMAIL")
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("_select"),
                        @ResourceVisibleColumn("_edit"),
                        @ResourceVisibleColumn("from"),
                        @ResourceVisibleColumn("subject"),
                        @ResourceVisibleColumn("content"),
                }))

)
public class ReceivedEmail extends AbstractDataDocumentWithClientLink {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "ReceivedEmail";
    private String subject;
    private List<ContentElement> content = new ArrayList<>();
    private List<String> receiver = new ArrayList<>();
    private List<String> from = new ArrayList<>();
    private String contentType;
    private DateTime dateReceived;
    private DateTime dateSend;
    private Map<String, String> headers = new HashMap<>();
    private List<DataDocumentLink<QueueEmail>> responseEmail = new ArrayList<>();

    /**
     * Instantiates a new Received email.
     */
    public ReceivedEmail() {


        super(TYPE_NAME);
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public List<ContentElement> getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(List<ContentElement> content) {
        this.content = content;
    }

    /**
     * Gets response email.
     *
     * @return the response email
     */
    public List<DataDocumentLink<QueueEmail>> getResponseEmail() {
        return responseEmail;
    }

    /**
     * Sets response email.
     *
     * @param responseEmail the response email
     */
    public void setResponseEmail(List<DataDocumentLink<QueueEmail>> responseEmail) {
        this.responseEmail = responseEmail;
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Constructor Simple app device.
     *
     * @return the headers
     */
// Constructor
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Sets headers.
     *
     * @param headers the headers
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public List<String> getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(List<String> from) {
        this.from = from;
    }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getDateReceived() {
        return dateReceived;
    }

    /**
     * Sets date received.
     *
     * @param dateReceived the date received
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setDateReceived(DateTime dateReceived) {
        this.dateReceived = dateReceived;
    }

    /**
     * Gets date send.
     *
     * @return the date send
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getDateSend() {
        return dateSend;
    }

    /**
     * Sets date send.
     *
     * @param dateSend the date send
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setDateSend(DateTime dateSend) {
        this.dateSend = dateSend;
    }

}
