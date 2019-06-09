package com.froso.ufp.modules.optional.facebookbot.service.fb;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.facebookbot.model.bot.*;
import java.io.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 16.08.2016.
 */
@Service
public class FBMessenger {

    private static final Logger LOGGER = LoggerFactory.getLogger(FBMessenger.class);
    private static final String PROPERTY_UFP_BOT_PAGETOKEN = "ufp.optional.facebook.bot.pageToken";
    private static final String FB_GRAPH_ENDPOINT = "https://graph.facebook.com/v2.6/me/messages";

    @Autowired
    private IPropertyService propertyService;


    /**
     * Send message string.
     *
     * @param message the message
     * @return the string
     */
    public String sendMessage(Messaging message) {

        LOGGER.info("Sending message back to facebook+[" + message.getMessage().getText() + "]");

        try {
            String slackWebhookUrlOnce;

            HttpClient client;
            slackWebhookUrlOnce = FB_GRAPH_ENDPOINT + "?access_token=" + propertyService.getPropertyValue(PROPERTY_UFP_BOT_PAGETOKEN);
            client = HttpClientBuilder.create().build();


            // Convert object to JSON string

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            String jsonInString = objectMapper.writeValueAsString(message);

            LOGGER.info("Sending message back to facebook+body is " + jsonInString);
            //   body.setText(stringBuilder.toString());
            // String jsonInString = mapper.writeValueAsString(body);
            HttpPost request = new HttpPost(slackWebhookUrlOnce);
            StringEntity params = new StringEntity(jsonInString, "UTF-8");
            params.setContentType("application/json");
            request.setEntity(params);

            HttpResponse response = client.execute(request);
            //     result.setSuccess(true);
            //   result.setStatusMessage(response.getEntity().toString());

            LOGGER.info("Sending message back to facebook+success" + response.getStatusLine());

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            StringBuilder builder = new StringBuilder();

            String output;
            LOGGER.info("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                LOGGER.info(output);
                builder.append(output);
                builder.append("/n");

            }
            return builder.toString();

        } catch (Exception e) {
            LOGGER.error("Facebook Message Send Error", e);
            //do nothin ....
            //      result.setSuccess(false);
            //       result.setStatusMessage(e.getMessage());
            return e.getMessage();
        }
    }

}
