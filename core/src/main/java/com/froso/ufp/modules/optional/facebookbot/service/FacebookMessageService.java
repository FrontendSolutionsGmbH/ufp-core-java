package com.froso.ufp.modules.optional.facebookbot.service;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.facebookbot.model.bot.*;
import com.google.common.base.*;
import java.io.*;
import javax.annotation.*;
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
public class FacebookMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookMessageService.class);
    private static final String PROPERTY_UFP_BOT_PAGETOKEN = "ufp.optional.facebook.bot.pageToken";
    private static final String FB_GRAPH_ENDPOINT = "https://graph.facebook.com/v2.6/me/";
    private static final String FB_GRAPH_ENDPOINT_MESSAGES = FB_GRAPH_ENDPOINT + "messages";
    private static final String FB_GRAPH_ENDPOINT_SUBSCRIBED_APPS = FB_GRAPH_ENDPOINT + "subscribed_apps";

    @Autowired
    private IPropertyService propertyService;


    /**
     * Gets user data.
     *
     * @param userId the user id
     * @return the user data
     */
    public String getUserData(String userId) {
        return userId;
    }

    @PostConstruct
    public void enableBotWithPostToFBGraphApi() {
        doSendActivation();

    }


    /**
     * Send actions string.
     *
     * @param senderActions the sender actions
     * @return the string
     */
    public String sendActions(SenderActions senderActions) {

        try {
            String slackWebhookUrlOnce;

            HttpClient client;
            slackWebhookUrlOnce = FB_GRAPH_ENDPOINT_MESSAGES + "?access_token=" + propertyService.getPropertyValue(PROPERTY_UFP_BOT_PAGETOKEN);
            client = HttpClientBuilder.create().build();


            // Convert object to JSON string

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
            String jsonInString = objectMapper.writeValueAsString(senderActions);

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

    /**
     * Send message string.
     *
     * @param message the message
     * @return the string
     */
    public String sendMessage(Messaging message) {
        String result = "";
        // check if incoming message is longer than 320
        if (message.getMessage().getText() != null && message.getMessage().getText().length() > 320) {
            // split message up

            Iterable<String> parts = Splitter.fixedLength(320).split(message.getMessage().getText());

            for (String string : parts) {
                message.getMessage().setText(string);

                result += dosendMessage(message);
            }
        } else {
            result += dosendMessage(message);
        }
        return result;
    }

    private String dosendMessage(Messaging message) {

        LOGGER.info("Sending message back to facebook+[" + message.getMessage().getText() + "]");

        try {
            String slackWebhookUrlOnce;

            HttpClient client;
            slackWebhookUrlOnce = FB_GRAPH_ENDPOINT_MESSAGES + "?access_token=" + propertyService.getPropertyValue(PROPERTY_UFP_BOT_PAGETOKEN);
            client = HttpClientBuilder.create().build();


            // Convert object to JSON string

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

    private void doSendActivation() {

        LOGGER.info("Activating bot");

        try {
            String slackWebhookUrlOnce;

            HttpClient client;
            slackWebhookUrlOnce = FB_GRAPH_ENDPOINT_SUBSCRIBED_APPS + "?access_token=" + propertyService.getPropertyValue(PROPERTY_UFP_BOT_PAGETOKEN);
            client = HttpClientBuilder.create().build();


            // Convert object to JSON string

            //   body.setText(stringBuilder.toString());
            // String jsonInString = mapper.writeValueAsString(body);
            HttpPost request = new HttpPost(slackWebhookUrlOnce);

            HttpResponse response = client.execute(request);
            LOGGER.info("Facebook Register response was " + response.toString());
            //     result.setSuccess(true);
            //   result.setStatusMessage(response.getEntity().toString());


        } catch (Exception e) {
            LOGGER.error("Facebook doSendActivation Error", e);
            //do nothin ....
            //      result.setSuccess(false);
            //       result.setStatusMessage(e.getMessage());

        }
    }

}
