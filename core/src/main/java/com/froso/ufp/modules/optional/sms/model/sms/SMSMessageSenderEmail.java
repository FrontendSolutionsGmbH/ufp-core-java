package com.froso.ufp.modules.optional.sms.model.sms;

import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.sms.model.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 21.06.2015.
 */
@Component
public class SMSMessageSenderEmail implements ISMSMessageSender {


    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "SMSEmail";

    @Override
    public ServiceStatus getServiceStatus() {

        ServiceStatus result = new ServiceStatus();
        result.setName(NAME);
        result.setIsWorking(true);

        return result;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public SMSResult sendSMS(QueueSms messageSMS) {
        SMSResult result = new SMSResult();
        result.setServiceName(NAME);
        return result;
    }

}
