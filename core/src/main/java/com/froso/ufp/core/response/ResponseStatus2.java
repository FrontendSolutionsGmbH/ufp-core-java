package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.messaging.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 01.11.13 Time: 15:19 A result status shall
 * contain textual information about the result, along with a statuscode, duration and the description of the status
 *
 * @param <T> the type parameter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseStatus2 implements IResponseHandler {

    private IResultStatusEnumCode status;

    private List<UFPResponseMessage> messages = new ArrayList<>();

    /**
     * Constructor Response status.
     */
    public ResponseStatus2() {
    }

    public void addMessage(String message) {


        messages.add(new UFPResponseMessage(message));

    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<UFPResponseMessage> getMessages() {
        return messages;
    }

    /**
     * Sets messages.
     *
     * @param messages the messages
     */

    /**
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(List<UFPResponseMessage> messages) {
        this.messages = messages;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public IResultStatusEnumCode getStatus() {

        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(IResultStatusEnumCode status) {

        this.status = status;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    @JsonView(JacksonViews.Minimal.class)
    public int getStatusCode() {

        return status.getCode();
    }


}
