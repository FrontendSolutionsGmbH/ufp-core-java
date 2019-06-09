package com.froso.ufp.modules.optional.sms.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.sms.model.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class LowLevelSMSSenderService {
    public static final String NAME = "SMSSenderService";

    private final SMSResourcesService smsFactory;

    private final IPropertyService propertyService;

    @Autowired
    public LowLevelSMSSenderService(SMSResourcesService smsFactory, IPropertyService propertyService) {
        this.propertyService = propertyService;
        this.smsFactory = smsFactory;

    }

    public QueueSms sendSMS(String phoneNumber, String message) {

        SMSData smsData = new SMSData();
        smsData.setPhoneNumber(phoneNumber);
        smsData.setText(message);
        QueueSms lowLevelSMS = new QueueSms();
        lowLevelSMS.setMessageData(smsData);
        lowLevelSMS.getInfo().setStatus(MessageStatusEnum.WAITING_TO_SEND);

        return lowLevelSMS;
    }

    public String getCurrentProviderName() {

        String providerName = propertyService.getPropertyValue("ufp.modules.sms.config.provider");
        return providerName;
    }

    public QueueSms sendSMS(QueueSms sms) {
        String usedProvider = getCurrentProviderName();
        ISMSProvider provider = smsFactory.getResource(usedProvider);
        if (provider != null) {
            SMSProviderResult result = provider.sendSMS(sms.getMessageData().getPhoneNumber(), sms.getMessageData().getText());

            sms.getInfo().setProviderUsed(usedProvider);
            sms.setResult(result);
        } else {
            throw new RuntimeException("SMS Provider not found: " + usedProvider);
        }
        return sms;
    }

}
