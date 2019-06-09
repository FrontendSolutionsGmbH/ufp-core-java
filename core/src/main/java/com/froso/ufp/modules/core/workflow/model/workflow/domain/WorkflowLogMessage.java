/*
 * Copyright (c) 2015.3.28 . FroSo, Christian Kleinhuis (ck@froso.de)
 */

package com.froso.ufp.modules.core.workflow.model.workflow.domain;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 28.03.2015.
 * <p>
 * this class collects relevant data for a running workflow, especially used for observing running import workflows
 */
public class WorkflowLogMessage implements Comparable<WorkflowLogMessage> {
    private MessageType type = MessageType.UNDEFINED;

    private DateTime time = DateTime.now();
    private String message;

    /**
     * factory method
     *
     * @param msg the msg
     * @return workflow log message
     */
    public static WorkflowLogMessage createWarning(String msg) {

        return create(MessageType.WARNING, msg);
    }

    /**
     * Create workflow log message.
     *
     * @param type the type
     * @param msg  the msg
     * @return the workflow log message
     */
    public static WorkflowLogMessage create(MessageType type,
                                            String msg) {

        WorkflowLogMessage result = new WorkflowLogMessage();
        result.setType(type);
        result.setMessage(msg);
        return result;
    }

    /**
     * factory method
     *
     * @param msg the msg
     * @return workflow log message
     */
    public static WorkflowLogMessage createInfo(String msg) {

        return create(MessageType.INFO, msg);
    }

    /**
     * factory method
     *
     * @param msg the msg
     * @return workflow log message
     */
    public static WorkflowLogMessage createError(String msg) {

        return create(MessageType.ERROR, msg);
    }

    public int compareTo(WorkflowLogMessage var1) {

        return Seconds.secondsBetween(var1.getTime(), time).getSeconds();
    }

    /**
     * Sets delivery date.
     *
     * @return the time
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getTime() {

        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setTime(final DateTime time) {

        this.time = time;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public MessageType getType() {

        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(final MessageType type) {

        this.type = type;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {

        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(final String message) {

        this.message = message;
    }

    @Override
    public String toString() {

        return type.toString() + " " + message;
    }
}
