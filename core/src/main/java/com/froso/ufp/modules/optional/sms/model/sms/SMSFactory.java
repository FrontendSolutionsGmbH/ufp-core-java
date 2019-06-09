package com.froso.ufp.modules.optional.sms.model.sms;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 28.06.2015.
 */
@Component
public class SMSFactory {

    /**
     * The constant PROPERTY_ADMIN_PHONENUMBER.
     */
    public static final String PROPERTY_ADMIN_PHONENUMBER = "sms.adminphonenumber";
    /**
     * The constant PROP_NAME_SMS_SERVICE.
     */
    public static final String PROP_NAME_SMS_SERVICE = "sms.service";
    @Autowired
    private SMSEmail smsEmail;

    @Autowired
    private SMSNull smsNull;


    /**
     * Gets sms interface.
     *
     * @return the sms interface
     */
    public ISMSMessageSender getSMSInterface(IPropertyService propertyService) {
        ISMSMessageSender result = null;

        switch (propertyService.getPropertyValue(PROP_NAME_SMS_SERVICE)) {

            case SMSEmail.NAME: {
                result = smsEmail;
                break;
            }
            default: {
                result = smsNull;
                break;
            }
        }
        return result;

    }

}
