package com.froso.ufp.modules.optional.authenticate.masterpassword.model;

import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class SMSRegisterData {
    /**
     * The constant TYPE_NAME.
     */

    @ApiModelProperty(example = "+49...")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
