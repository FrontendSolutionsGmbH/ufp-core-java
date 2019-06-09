package com.froso.ufp.modules.optional.clickatelltest.model;

import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * Created by mr on 06.06.16.
 */
public class ClickatellSendSmsRequest {


    private final String defaultMessage = "TestMessage";

    private String message;
    @ApiModelProperty(required = true)
    @NotNull
    private String to;


    // Constructor
    public ClickatellSendSmsRequest() {

    }

    public ClickatellSendSmsRequest(String to, String message) {
        this.to = to;
        this.message = message;
        if (this.message == null) {
            this.message = defaultMessage;
        }
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
