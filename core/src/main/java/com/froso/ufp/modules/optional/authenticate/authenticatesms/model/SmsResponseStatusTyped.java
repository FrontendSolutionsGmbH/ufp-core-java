package com.froso.ufp.modules.optional.authenticate.authenticatesms.model;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;

public class SmsResponseStatusTyped extends ResponseStatusTyped<SMSResultStatusEnumCode> implements IResponseStatusTyped<SMSResultStatusEnumCode> {


    public SmsResponseStatusTyped(){
        super();
        // important, set default status value
        this.setStatus(SMSResultStatusEnumCode.OK);
    }
}
