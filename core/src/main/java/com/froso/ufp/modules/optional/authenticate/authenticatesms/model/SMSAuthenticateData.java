package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;

@ApiModel(description = "Authenticate against UFP Backend using a received Nonce")
public class SMSAuthenticateData implements IDataObject {
    @ApiModelProperty(hidden=true,example = "nonceValue", position = 100, notes = "The Nonce code send to the phone number")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NonceData nonceData;
    @Indexed(unique = true,background = true)
    @ApiModelProperty(example = "+49...", position = 0, notes = "The ponenumber that the Nonce code hase been send to")
    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public NonceData getNonceData() {
        return nonceData;
    }
    public void setNonceData(NonceData nonceData) {
        this.nonceData = nonceData;
    }
}
