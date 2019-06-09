package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.messaging.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 01.11.13 Time: 15:19 A result status shall
 * contain textual information about the result, along with a statuscode, duration and the description of the status
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseStatus {

    @JsonView(JacksonViews.Minimal.class)
    private IResultStatusEnumCode status;
    private long durationMilliSeconds;

    private List<UFPResponseMessage> messages;

    /**
     * Constructor Response status.
     */
    public ResponseStatus() {

        status = ResultStatusEnumCode.OK;
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

    /**
     * Gets duration milli seconds.
     *
     * @return the duration milli seconds
     */
    public long getDurationMilliSeconds() {

        return durationMilliSeconds;
    }

    /**
     * Sets duration milli seconds.
     *
     * @param durationNanoSseconds the duration nano sseconds
     */
    public void setDurationMilliSeconds(long durationNanoSseconds) {

        this.durationMilliSeconds = durationNanoSseconds;
    }

}
