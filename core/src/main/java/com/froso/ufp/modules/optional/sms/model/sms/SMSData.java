package com.froso.ufp.modules.optional.sms.model.sms;

import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * Created with IntelliJ IDEA.
 * Entiteit: ck
 * Date: 24.03.14
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class SMSData extends MessageDataBase {

    @NotNull
    @ApiModelProperty(hidden = false, required = false, notes = "A valid telephone number")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
