package com.froso.ufp.modules.optional.sms.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.sms.model.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class LowLevelSMSService extends AbstractClientRefService<QueueSms> {

    public QueueSms sendSMS(String phoneNumber, String message, DataDocumentLink coreUserLink) {
        if (null == coreUserLink) {

            return sendSMS(phoneNumber, message);
        } else {
            return sendSMS(phoneNumber, message, coreUserLink.getId());
        }
    }

    public QueueSms sendSMS(String phoneNumber, String message, String coreUserId) {

        SMSData smsData = new SMSData();
        smsData.setPhoneNumber(phoneNumber);
        smsData.setText(message);
        QueueSms queueSms = new QueueSms();
        queueSms.setMessageData(smsData);
        queueSms.getInfo().setStatus(MessageStatusEnum.WAITING_TO_SEND);

        if (null != coreUserId) {
            queueSms.setCoreUser(new DataDocumentLink<>(coreUserId));
        }
        return save(queueSms);

    }

    public QueueSms sendSMS(String phoneNumber, String message) {
        return sendSMS(phoneNumber, message, (String) null);

    }

    public List<QueueSms> findAllNew(Pageable p) {
        return findByKeyValue("info.status", "=" + MessageStatusEnum.WAITING_TO_SEND);
    }

    public List<QueueSms> findAllErrornous(Pageable p) {
        return findByKeyValue("info.status", "=" + "ERROR");

    }
}
