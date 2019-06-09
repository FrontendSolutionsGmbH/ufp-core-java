package com.froso.ufp.modules.optional.messaging.model.messaging;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.user.model.*;
import io.swagger.annotations.*;

/**
 * Created with IntelliJ IDEA. CoreUser: ck Date: 24.03.14 Time: 13:36 To change this template use File | Settings | File
 * Templates.
 */

public abstract class MessageBase<T> extends AbstractDataDocumentWithCoreUserLink {

    private static final long serialVersionUID = 2814544637327573705L;
    private VelocityMessage velocityMessage;
    private T messageData;

    @ApiModelProperty(hidden = false, required = true, notes = "Current status of the message")
    private MessageStatusEnum status = MessageStatusEnum.WAITING_TO_SEND;
    @ApiModelProperty(hidden = false, required = true, notes = "Retry count, after N retries it is cancelled totally")
    private Integer retryCount = 0;
    @ApiModelProperty(hidden = false, required = true, notes = "Any error message is stored here")
    private String errorMessage;

    /**
     * Constructor Simple email.
     */
    // Constructor
    public MessageBase(String typeName, T messageDataIn) {

        super(typeName);
        messageData = messageDataIn;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public MessageStatusEnum getStatus() {

        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(MessageStatusEnum status) {

        this.status = status;
    }

    /**
     * Gets velocity email.
     *
     * @return the velocity email
     */
    public VelocityMessage getVelocityMessage() {

        return velocityMessage;
    }

    /**
     * Sets velocity email.
     *
     * @param velocityMessage the velocity email
     */
    public void setVelocityMessage(VelocityMessage velocityMessage) {

        this.velocityMessage = velocityMessage;
    }

    /**
     * Gets retry count.
     *
     * @return the retry count
     */
    public Integer getRetryCount() {

        return retryCount;
    }

    /**
     * Sets retry count.
     *
     * @param retryCount the retry count
     */
    public void setRetryCount(Integer retryCount) {

        this.retryCount = retryCount;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {

        return errorMessage;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }

    public T getMessageData() {
        return messageData;
    }

    public void setMessageData(T messageData) {
        this.messageData = messageData;
    }
}
