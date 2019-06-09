package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

@ApiModel(description = "Request a Nonce (number-used-once) to authenticate against UFP Backend")
public class SMSAuthenticateRequestDataPhonenumberOnly implements IDataObject {

    @ApiModelProperty(example = "+49...", notes = "The telephone number to send the Nonce to")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
