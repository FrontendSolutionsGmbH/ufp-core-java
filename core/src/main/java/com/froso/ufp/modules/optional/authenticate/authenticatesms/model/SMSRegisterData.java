package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

@ApiModel(description = "Telephone number associated with account")
public class SMSRegisterData implements IDataObject {

    @ApiModelProperty(example = "+49...", notes = "The telephone number to register")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
