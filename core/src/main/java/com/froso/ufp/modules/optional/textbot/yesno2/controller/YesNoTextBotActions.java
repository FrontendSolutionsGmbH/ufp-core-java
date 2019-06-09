package com.froso.ufp.modules.optional.textbot.yesno2.controller;

import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.facebookbot.service.*;
import com.froso.ufp.modules.optional.textbot.yesno2.model.*;
import com.froso.ufp.modules.optional.textbot.yesno2.service.*;
import java.util.*;
import java.util.regex.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
@TextBotAnnotation
public class YesNoTextBotActions {
    //    @Autowired
//    private ExtendedTextBotService extendedTextBotService;

    /**
     * The constant YES_ACTION.
     */
    public static final String YES_ACTION = "YES";
    /**
     * The constant NO_ACTION.
     */
    public static final String NO_ACTION = "NO";
    private static final Logger LOGGER = LoggerFactory.getLogger(YesNoTextBotActions.class);
    @Autowired
    private YesNoTextBotService yesNoTextBotService;
    @Autowired
    private YesNoTextBotConfigService yesNoTextBotConfigService;

    @Autowired
    private FacebookBotService receivedFacebookDataService;

    /**
     * Text bot handler text bot string result.
     *
     * @param message      the message
     * @param templateData the template data
     * @return the text bot string result
     */
    @TextBotMethodAnnotation(regex = ".*",
            description = "YesNoTextBotHandler",
            command = "",
            hidden = true)
    public TextBotStringResult textBotHandler(String message, Map<String, Object> templateData) {
        String answer = null;

        TextBotStringResult result = null;
        Pattern pattern = Pattern.compile(".*\\?.*");
        Pattern pattern2 = Pattern.compile("yesNo \\((.*),(.*)\\)");

        Matcher m = pattern.matcher(message);
        Matcher m2 = pattern2.matcher(message);
        if (m.matches()) {            //   if (m.matches()) {


            YesNoTextBotModel model = yesNoTextBotService.createNewDefault();
            model.setText(message);


            result = new TextBotButtonResult();
            TextBotButton quickButton = TextBotButton.createPostbackButton(YES_ACTION, "yesNo (" + model.getId() + "," + YES_ACTION + ")");
            ((TextBotButtonResult) result).getButtons().add(quickButton);
            TextBotButton quickButton2 = TextBotButton.createPostbackButton(NO_ACTION, "yesNo (" + model.getId() + "," + NO_ACTION + ")");
            ((TextBotButtonResult) result).getButtons().add(quickButton2);
            result.setText(message);

            yesNoTextBotService.save(model);


            // -------------- call init url ------------------
            /*
            String urlVoteView = config.getVoteViewUrl();

            urlVoteView += "?id=" + model.getId();

            HttpUriRequest request = null;
            request = new HttpGet(config.getUrlToCall().replaceAll("\\$1", URLEncoder.encode(urlVoteView)));
            try {
                HttpClient client = HttpClientBuilder.create().build();
                client.execute(request);

            } catch (IOException e) {
                answer = "(" + message + ")" + "Error " + e.getMessage();
            }
                     */
            // ---------------------- call init url end


            // fixme: todo use distinct query
            // get all known user ids
            List<ReceivedFacebookBotMessage> allMessages = receivedFacebookDataService.findAll();
            Set<String> distinctUserIds = new HashSet<>();
            for (ReceivedFacebookBotMessage receivedFacebookBotMessage : allMessages) {

                try {
                    distinctUserIds.add(receivedFacebookBotMessage.getDataMessage().getEntry().get(0).getMessaging().get(0).getSender().getId());
                } catch (Exception e) {
                    LOGGER.error("YESNOBOTERROR COLLECTING USERS ", e);
                    //    answer += e.getMessage();
                }

            }
            for (String item : distinctUserIds) {
                Recipient recipient = new Recipient();
                recipient.setRecipientId(item);
                result.getRecipients().add(recipient);
            }
            result.setText(result.getText());
        }

        if (m2.matches()) {

            YesNoTextBotModel model = yesNoTextBotService.findOneBrute(m2.group(1));
            if (model != null) {

                if (YES_ACTION.equals(m2.group(2))) {

                    model.setYesCount(model.getYesCount() + 1);
                    answer = "COUNTED YES!" + "YES:" + model.getYesCount() + " NO: " + model.getNoCount();
                } else if (NO_ACTION.equals(m2.group(2))) {
                    model.setNoCount(model.getNoCount() + 1);
                    answer = "COUNTED NO!" + "YES:" + model.getYesCount() + " NO: " + model.getNoCount();

                }

                yesNoTextBotService.save(model);

                                  /*
           String urlVoteView = config.getVoteViewUrl();

                urlVoteView += "?yes=" + model.getYesCount();
                urlVoteView += "&no=" + model.getNoCount();
                urlVoteView += "&nameValue=" + URLEncoder.encode(model.getText());

                HttpUriRequest request = null;
                request = new HttpGet(config.getUrlToCall().replaceAll("\\$1", URLEncoder.encode(urlVoteView)));
                try {
                    HttpClient client = HttpClientBuilder.create().build();
                    client.execute(request);

                } catch (IOException e) {
                    answer = "(" + message + ")" + "Error " + e.getMessage();
                }
                                    */

            } else {
                answer = "Question not found (" + message + ")";

            }

            result = new TextBotStringResult(answer);


        }
        return result;
    }
}
