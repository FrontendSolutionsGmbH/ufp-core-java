package com.froso.ufp.modules.optional.smsprovider.clickatellsmsprovider.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by mr on 07.06.16.
 */

@Component
public class ClickatellSmsProvider implements ISMSProvider {

    public static final String CLICKATELL_AUTHTOKEN = "ufp.modules.sms.provider.clickatell.authtoken";
    public static final String CLICKATELL_SMS_PROVIDER_NAME = "clickatell";
    @Autowired
    IPropertyService propertyService;

    public SMSProviderResult sendSMS(String numbers, String message) {
        ClickatellRest smsRestService = new ClickatellRest(propertyService.getPropertyValue(CLICKATELL_AUTHTOKEN));

        SMSProviderResult result = new SMSProviderResult();
        result.setProvider(getName());

        try {
            ClickatellRest.Message msg = smsRestService.sendMessage(numbers, message);
            if (msg.message_id != null) {
                result.setSuccess(true);
                result.setExternalId(msg.message_id);
                result.setStatusMessage(msg.error);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setStatusMessage("auth error");
        }
        return result;
    }


    public Boolean health() {
        return true;
    }


    public String getName() {
        return CLICKATELL_SMS_PROVIDER_NAME;
    }
}
