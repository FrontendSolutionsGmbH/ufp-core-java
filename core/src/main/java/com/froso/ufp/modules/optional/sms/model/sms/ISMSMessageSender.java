package com.froso.ufp.modules.optional.sms.model.sms;

import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.sms.model.*;

/**
 * Created by ckleinhuix on 21.06.2015.
 */
public interface ISMSMessageSender extends ServiceStatusReporter {


    SMSResult sendSMS(QueueSms messageSMS);

}
