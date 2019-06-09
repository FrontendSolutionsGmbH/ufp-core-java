package com.froso.ufp.modules.optional.sms.model.sms;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 21.06.2015.
 */
@Component
public class SMSProviderEmail implements ISMSProvider {

    /**
     * The constant SMS_TO_EMAIL_PROPERTY.
     */
    public static final String SMS_TO_EMAIL_PROPERTY = "ufp.modules.sms.provider.email.to";
    /**
     * The constant SMS_SUBJECT_EMAIL_PROPERTY.
     */
    public static final String SMS_SUBJECT_EMAIL_PROPERTY = "ufp.modules.sms.provider.email.subject";
    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "sms-email";

    @Autowired(required = false)
    private EmailSenderService emailSenderService;

    @Autowired(required = true)
    private IPropertyService propertyService;

    public SMSProviderResult sendSMS(String number, String nessage) {
        SMSProviderResult result = new SMSProviderResult();

        emailSenderService.sendMail(propertyService.getPropertyValue(SMS_TO_EMAIL_PROPERTY), propertyService.getPropertyValue(SMS_SUBJECT_EMAIL_PROPERTY), nessage);
        result.setSuccess(true);

        return result;
    }

    @Override
    public Boolean health() {
        return emailSenderService == null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
