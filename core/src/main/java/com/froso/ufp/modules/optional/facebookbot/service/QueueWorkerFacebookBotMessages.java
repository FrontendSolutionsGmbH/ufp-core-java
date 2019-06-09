package com.froso.ufp.modules.optional.facebookbot.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.*;
import com.froso.ufp.modules.optional.facebookbot.model.bot.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

/**
 * The type Queue worker email sender.
 */
@Service
public class QueueWorkerFacebookBotMessages {

    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "QueueWorkerBotMessages";
    /**
     * The constant MAX_BUTTON_COUNT_FACEBOOK.
     */
    public static final int MAX_BUTTON_COUNT_FACEBOOK = 3;
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueWorkerFacebookBotMessages.class);
    private final AtomicBoolean processingEmails = new AtomicBoolean(false);
    /**
     * The Application property service.
     */
    @Autowired
    GlobalsService globalsService;
    @Autowired
    private FacebookBotMethodService facebookBotMethodService;
    @Autowired
    private FacebookMessageService facebookMessageService;
    /**
     * The Thread pool task executor.
     */
    @Autowired
    private FacebookBotService facebookBotService;


    /**
     * Work bot messages.
     */
//@Scheduled(fixedRateString = "${sendemail.updateIntervalMilliseconds}")
    //@Async
    @WorkerAnnotation(name = "TextBot Message worker", description = "this process works through all incoming bot text messages and creates responses for each")
    public void workBotMessages() {


        if (processingEmails.get() == false) {
            //      LOGGER.info("QUEUE Check Bot Messages");
            processingEmails.set(true);
            Pageable pageable = new PageRequest(0, 10, Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
            List<ReceivedFacebookBotMessage> messages = facebookBotService.findAllNew(pageable);


            Map<String, Object> parammap = new HashMap<>();
            for (ReceivedFacebookBotMessage data : messages) {
                handleEntry(data);
            }
            processingEmails.set(false);
        }
    }

    /**
     * Handle entry.
     *
     * @param data the data
     */
    public void handleEntry(ReceivedFacebookBotMessage data) {
        Map<String, Object> parammap = new HashMap<>();


        try {
            LOGGER.info("Processing FB Bot messages " + data.getId());

            // handle message
            for (PageData page : data.getData().getEntry()) {


                parammap.put("data", page);

                for (Messaging entry : page.getMessaging()) {

                    LOGGER.info("Processing FB Bot messages [" + entry.getMessage().getText() + "]");
                    TextBotResult temp;
                    if (entry.getPostback() != null) {
                        //       facebookMessageService.sendActions(SenderActions.constructTyping(data.getData().getEntry().get(0).getMessaging().get(0).getSender().getId()));
                        temp = facebookBotMethodService.handleByString(entry.getPostback().getPayload(), parammap);
                    } else if (entry.getMessage().getQuick_reply() != null) {

                        //   facebookMessageService.sendActions(SenderActions.constructTyping(data.getData().getEntry().get(0).getMessaging().get(0).getSender().getId()));
                        temp = facebookBotMethodService.handleByString(entry.getMessage().getQuick_reply().getPayload(), parammap);
                    } else {
                        //       facebookMessageService.sendActions(SenderActions.constructTyping(data.getData().getEntry().get(0).getMessaging().get(0).getSender().getId()));
                        temp = facebookBotMethodService.handleByString(entry.getMessage().getText(), parammap);
                    }
                    LOGGER.info("Processing FB Bot messages +[" + temp + "]");


                    //   facebookMessageService.sendActions(SenderActions.constructTypingFinished(data.getData().getEntry().get(0).getMessaging().get(0).getSender().getId()));
                    if (temp != null) {

                        LOGGER.info("Sending message back to facebook+[" + temp + "]");
                        if (temp instanceof ITextBotButtonsResult) {


                            if (temp.getRecipients().isEmpty()) {
                                handleTextBotButtonsResult((ITextBotButtonsResult) temp, data, entry.getSender().getId());
                            } else {

                                for (Recipient recipient : temp.getRecipients()) {

                                    handleTextBotButtonsResult((ITextBotButtonsResult) temp, data, recipient.getRecipientId());


                                }

                            }


                        } else if (temp instanceof ITextBotQuickButtonsResult) {
                            handleTextBotQuickButtonsResult((ITextBotQuickButtonsResult) temp, data, entry.getSender().getId());
                        } else if (temp instanceof ITextBotStringResult) {

                            handleTextBotStringResult((ITextBotStringResult) temp, data, entry.getSender().getId());
                        }
                    } else {
                        data.setStatus(FacebookBotMessageStatus.UNHANDLED);
                        facebookBotService.save(data);
                    }

                }

            }
        } catch (Exception e) {

            LOGGER.info("error handling message", e);
            data.getAdditionalProperties().put("exception", e.getMessage());

        } finally {
            facebookBotService.save(data);

            data.setStatus(FacebookBotMessageStatus.PROCESSED);
            facebookBotService.save(data);

        }

    }

    private void handleTextBotStringResult(ITextBotStringResult textBotStringResult, ReceivedFacebookBotMessage data, String receiverId) {


        data.setResponseTextSend(textBotStringResult.getText());
        data.setStatus(FacebookBotMessageStatus.PROCESSED);
        facebookBotService.save(data);
        Messaging response = new Messaging();
        response.setRecipient(IDObject.construct(receiverId));
        response.getMessage().setText(textBotStringResult.getText());


        data.getSendToFacebookData().put("sendToFacebook", response);

        String result = facebookMessageService.sendMessage(response);

        data.getSendToFacebookData().put("resultFromFacebook", result);
        facebookBotService.save(data);
    }

    private void handleTextBotButtonsResult(ITextBotButtonsResult textBotButtonsResult, ReceivedFacebookBotMessage data, String receiverId) {

// transfomr incomin textbot format to native facebook message format

        data.setResponseTextSend(textBotButtonsResult.getText());
        data.setStatus(FacebookBotMessageStatus.PROCESSED);
        Messaging response = new Messaging();
        response.setRecipient(IDObject.construct(receiverId));
        Message message = new Message();
        Attachment attachment = new Attachment();
        attachment.setType("template");


        AnyPayload payload = new AnyPayload();
        payload.setText(textBotButtonsResult.getText());
        payload.setTemplate_type("button");
        message.setAttachment(attachment);
        attachment.setPayload(payload);
        List<Button> buttons = new ArrayList<>();
        for (ITextBotButton button : textBotButtonsResult.getButtons()) {
            Button button1 = new Button();

            button1.setPayload(button.getPayload());
            button1.setTitle(button.getTitle());
            button1.setType(button.getButtonType() == ButtonType.POSTBACK ? "postback" : "web_url");
            button1.setUrl(button.getUrl());

            // strip of max allowed buttons
            if (buttons.size() < MAX_BUTTON_COUNT_FACEBOOK) {
                LOGGER.warn("Too many buttons, cutting of any obove allowed max buttons");
                buttons.add(button1);
            }

        }
        payload.setButtons(buttons);
        response.setMessage(message);

        data.getSendToFacebookData().put("sendToFacebook", response);

        String result = facebookMessageService.sendMessage(response);

        data.getSendToFacebookData().put("resultFromFacebook", result);
        facebookBotService.save(data);
    }

    private void handleTextBotQuickButtonsResult(ITextBotQuickButtonsResult textBotButtonsResult, ReceivedFacebookBotMessage data, String receiverId) {

// transfomr incomin textbot format to native facebook message format

        data.setResponseTextSend(textBotButtonsResult.getText());
        data.setStatus(FacebookBotMessageStatus.PROCESSED);
        Messaging response = new Messaging();
        response.setRecipient(IDObject.construct(receiverId));
        Message message = new Message();


        message.setText(textBotButtonsResult.getText());

        List<QuickButton> quickReplies = new ArrayList<>();
        // payload.setTemplate_type("button");
        for (ITextBotQuickButton button : textBotButtonsResult.getQuick_replies()) {
            QuickButton button1 = new QuickButton();

            button1.setPayload(button.getPayload());
            button1.setTitle(button.getTitle());

            quickReplies.add(button1);


        }
        message.setQuick_replies(quickReplies);
        response.setMessage(message);

        data.getSendToFacebookData().put("sendToFacebook", response);

        String result = facebookMessageService.sendMessage(response);

        data.getSendToFacebookData().put("resultFromFacebook", result);
        facebookBotService.save(data);
    }


}
