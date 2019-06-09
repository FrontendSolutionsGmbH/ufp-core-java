package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

@ApiModel(description = "Authenticate against UFP Backend using a received Nonce")
public class SMSAuthenticateRequestData implements IDataObject {
    @ApiModelProperty(example = "nonceValue", position = 100, notes = "The Nonce code send to the phone number")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String nonce;
    @ApiModelProperty(example = "+49...", position = 0, notes = "The ponenumber that the Nonce code hase been send to")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getNonce() {
        return nonce;
    }
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
