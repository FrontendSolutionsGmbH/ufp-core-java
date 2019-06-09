package com.froso.ufp.modules.optional.smsprovider.thetexting.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.net.*;

@Component
public class TheTextingSmsProvider implements ISMSProvider {

    public static final String API_URI_SEND_SMS = "/rest/sms/json/message/send";
    public static final String PROP_NAME_THETEXTING_API_URL = "ufp.modules.sms.provider.thetexting.api_url";
    public static final String PROP_NAME_THETEXTING_API_KEY = "ufp.modules.sms.provider.thetexting.api_key";
    public static final String PROP_NAME_THETEXTING_API_SECRET = "ufp.modules.sms.provider.thetexting.api_secret";
    public static final String PROP_NAME_THETEXTING_FROM = "ufp.modules.sms.provider.thetexting.from";
    public static final String THETEXTING_SMS_PROVIDER_NAME = "sms-thetexting";
    private static final Logger LOGGER = LoggerFactory.getLogger(TheTextingSmsProvider.class);

    private final IPropertyService propertyService;

    @Autowired
    public TheTextingSmsProvider(IPropertyService propertyService) {
        this.propertyService = propertyService;

    }

    public SMSProviderResult sendSMS(String numbers, String message) {

        LOGGER.info("Send TheTexting SMS Message {} {}", numbers, message);

        SMSProviderResult result = new SMSProviderResult();
        try {

            HttpClient client;
            client = HttpClientBuilder.create().build();

            StringBuilder body = new StringBuilder();
            body.append("api_secret");
            body.append("=");
            body.append(propertyService.getPropertyValue(PROP_NAME_THETEXTING_API_SECRET));

            body.append("&");
            body.append("api_key");
            body.append("=");
            body.append(propertyService.getPropertyValue(PROP_NAME_THETEXTING_API_KEY));

            body.append("&");
            body.append("from");
            body.append("=");
            body.append(propertyService.getPropertyValue(PROP_NAME_THETEXTING_FROM));
            body.append("&");
            body.append("text");
            body.append("=");
            body.append(URLEncoder.encode(message));
            body.append("&");
            body.append("to");
            body.append("=");
            body.append(numbers);
            body.append("&");
            body.append("type");
            body.append("=");
            body.append("text");

//            String jsonInString = mapper.writeValueAsString(body);
            HttpPost request = new HttpPost(propertyService.getPropertyValue(PROP_NAME_THETEXTING_API_URL) + API_URI_SEND_SMS + "?" + body.toString());
//            StringEntity params = new StringEntity(body.toString());

            request.addHeader("content-type", "application/x-www-form-urlencoded");
//            request.setEntity(params);
            HttpResponse response = client.execute(request);
            result.setSuccess(true);
            result.setStatusMessage(EntityUtils.toString(response.getEntity()));
            LOGGER.info("Response Body {} {}", response.getStatusLine().getStatusCode(), result.getStatusMessage());
            LOGGER.info("Response Body {} {}", result.getStatus(), result.getStatusMessage());
        } catch (Exception e) {
            LOGGER.error("Problem sending thetexting sms", e);
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
        return THETEXTING_SMS_PROVIDER_NAME;
    }
}
