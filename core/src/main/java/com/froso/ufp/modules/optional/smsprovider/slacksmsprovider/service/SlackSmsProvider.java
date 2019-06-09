package com.froso.ufp.modules.optional.smsprovider.slacksmsprovider.service;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import com.froso.ufp.modules.optional.sms.service.*;
import com.froso.ufp.modules.optional.smsprovider.slacksmsprovider.model.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by mr on 07.06.16.
 */

@Component
public class SlackSmsProvider implements ISMSProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackSmsProvider.class);
    public static final String PROP_NAME_SLACK_WEBHOOK = "ufp.modules.sms.provider.slack.webhook";
    public static final String SLACK_SMS_PROVIDER_NAME = "sms-slack";

    @Autowired
    IPropertyService propertyService;
    @Autowired
    UfpCoreService ufpCoreService;


    public SMSProviderResult sendSMS(String numbers, String message) {

        LOGGER.info("Send Slack SMS Message {} {}", numbers, message);

        SMSProviderResult result = new SMSProviderResult();
        try {
            String slackWebhookUrlOnce;

            HttpClient client;
            slackWebhookUrlOnce = propertyService.getPropertyValue(PROP_NAME_SLACK_WEBHOOK);
            client = HttpClientBuilder.create().build();

            ObjectMapper mapper = new ObjectMapper();
            SlackWebhookBody body = new SlackWebhookBody();


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FakeSms to ");
            stringBuilder.append(numbers);
            stringBuilder.append("\n");
            stringBuilder.append(" Text: ");
            stringBuilder.append("\"" + message + "\"");
            stringBuilder.append("\n");
            stringBuilder.append("App: " + ufpCoreService.getApplicationVersionString());
            stringBuilder.append("\n");
            stringBuilder.append("Host: ");
            stringBuilder.append("<" + ufpCoreService.getApplicationHostName() + ">");


            body.setText(stringBuilder.toString());
            String jsonInString = mapper.writeValueAsString(body);
            HttpPost request = new HttpPost(slackWebhookUrlOnce);
            StringEntity params = new StringEntity(jsonInString);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);
            result.setSuccess(true);
            result.setStatusMessage(response.getEntity().toString());

        } catch (Exception e) {
            LOGGER.error("Problem sending slack sms {}", e);
            //do nothin ....
            result.setSuccess(false);
            result.setStatusMessage(e.getMessage());

        }
        return result;
    }


    public Boolean health() {
        return true;
    }


    public String getName() {
        return SLACK_SMS_PROVIDER_NAME;
    }
}
