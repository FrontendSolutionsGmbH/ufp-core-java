package com.froso.ufp.modules.optional.messaging.model.messaging;

import io.swagger.annotations.*;

public class LowLevelMessageStatus {

    @ApiModelProperty(hidden = false, required = true, notes = "Current status of the message")
    private MessageStatusEnum status = MessageStatusEnum.IDLE;
    @ApiModelProperty(hidden = false, required = true, notes = "Retry count, after N retries it is cancelled totally")
    private Integer retryCount = 0;
    @ApiModelProperty(hidden = false, required = true, notes = "Any error message is stored here")
    private String errorMessage;

    @ApiModelProperty(hidden = false, required = true, notes = "The used provider for the last request attempt")
    private String providerUsed;

    public MessageStatusEnum getStatus() {

        return status;
    }

    public void setStatus(MessageStatusEnum status) {

        this.status = status;
    }

    public String getProviderUsed() {
        return providerUsed;
    }

    public void setProviderUsed(String providerUsed) {
        this.providerUsed = providerUsed;
    }

    public Integer getRetryCount() {

        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {

        this.retryCount = retryCount;
    }

    public String getErrorMessage() {

        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }
}
