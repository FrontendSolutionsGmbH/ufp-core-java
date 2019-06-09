package com.froso.ufp.modules.optional.sms.model.sms;

import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 21.06.2015.
 */

@Component
public class SMSProviderNull implements ISMSProvider {


    public static final String NAME = "sms-null";

    public SMSProviderResult sendSMS(String number, String nessage) {
        SMSProviderResult result = new SMSProviderResult();
        result.setSuccess(true);
        return result;

    }

    public Boolean health() {
        return true;
    }

    public String getName() {
        return NAME;
    }

}
