package com.froso.ufp.modules.optional.sms.model.sms;

/**
 * Created by ckleinhuix on 21.06.2015.
 */
public interface ISMSProvider {


    SMSProviderResult sendSMS(String numbers, String message);


    Boolean health();

    String getName();

}
