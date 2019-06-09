package com.froso.ufp.modules.optional.facebookbot.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.facebookbot.model.*;
import com.froso.ufp.modules.optional.facebookbot.model.bot.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.facebookbot.service.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/fb/bot")
@Api(description = "UFP FB Messenger Api",
        tags = "ReceivedFacebookBotMessage")
class FacebookBotController {
    @Autowired
    private FacebookBotService facebookBotService;
    @Autowired
    private FacebookBotMethodService facebookBotMethodService;
    @Autowired
    private FacebookMessageService facebookMessageService;
    @Autowired
    private QueueWorkerFacebookBotMessages queueWorkerFacebookBotMessages;

    /**
     * Instantiates a new Client controller.
     *
     * @param dataInParsedRaw   the data in parsed raw
     * @param dataParsedMessage the data parsed message
     * @param request           the request
     * @return the response entity
     */
    @ApiOperation(value = "facebook chat bot endpoint - message",
            notes = "Experimental FB Bot Endpoint. For now it just stores any incoming data")
    @RequestMapping(value = "/message",
            method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateEmpty> facebookBotEndpointMessage(
            @RequestBody
                    Map<String, Object> dataInParsedRaw,
            @RequestBody(required = false)
                    CallbackData<PageData> dataParsedMessage,
            HttpServletRequest request) {
        ResponseHandlerTemplateEmpty manager = new ResponseHandlerTemplateEmpty(request);
        ReceivedFacebookBotMessage receivedFacebookBotMessage = new ReceivedFacebookBotMessage();
        receivedFacebookBotMessage.setCreator("message");
        if (dataParsedMessage.getEntry() != null) {
            receivedFacebookBotMessage.setData(dataParsedMessage);
            // just send back message received
            facebookMessageService.sendActions(SenderActions.constructMessageSeen(receivedFacebookBotMessage.getData().getEntry().get(0).getMessaging().get(0).getSender().getId()));
        }
        receivedFacebookBotMessage.setReceivedData(dataInParsedRaw);

        try {
            receivedFacebookBotMessage.setReceivedText(dataParsedMessage.getEntry().get(0).getMessaging().get(0).getMessage().getText());
        } catch (Exception e) {

            // try reading something else
            try {
                receivedFacebookBotMessage.setReceivedText(dataParsedMessage.getEntry().get(0).getMessaging().get(0).getPostback().getPayload());

            } catch (Exception e2) {


                receivedFacebookBotMessage.setReceivedText(e.getMessage() + '=' + e.getMessage());
            }

        }


        if (facebookBotService.isTextBotModeInstant()) {
            // if process mode is instant, pass it through workerservice
            queueWorkerFacebookBotMessages.handleEntry(receivedFacebookBotMessage);

            facebookBotService.save(receivedFacebookBotMessage);
        } else {
            // if not instantan, save in process queue
            facebookBotService.save(receivedFacebookBotMessage);
        }
        return manager.getResponseEntity();
    }

    /**
     * Facebook verify callback string.
     *
     * @param hub     the hub
     * @param request the request
     * @return the string
     */
    @ApiOperation(value = "facebook verify request endpoint",
            notes = "this endpoint is used by the facebook api to identify/verify our bot")
    @RequestMapping(value = "/message",
            method = RequestMethod.GET)
    @ResponseBody
    public String facebookVerifyCallback(
            FacebookSecurityHubRequest hub, HttpServletRequest request) {
        ReceivedFacebookBotMessage receivedFacebookBotMessage = new ReceivedFacebookBotMessage();
        receivedFacebookBotMessage.setCreator("securityHub");
        Map<String, Object> testmap = new HashMap<>();
        testmap.put("hub", hub);
        receivedFacebookBotMessage.setAdditionalProperties(testmap);
        facebookBotService.save(receivedFacebookBotMessage);
        // verivfiy incoming bot data
        return facebookBotService.verifyFacebookSecurityHub(hub.getHub());
    }

    /**
     * Facebook test method handlers string.
     *
     * @param message the message
     * @param request the request
     * @return the string
     */
    @ApiOperation(value = "facebook verify request endpoint",
            notes = "this endpoint is used by the facebook api to identify/verify our bot")
    @RequestMapping(value = "/test",
            method = RequestMethod.GET)
    @ResponseBody
    public String facebookTestMethodHandlers(
            @RequestParam
                    String message, HttpServletRequest request) {
        TextBotResult result = facebookBotMethodService.handleByString(message, new HashMap<String, Object>());
        if (result instanceof ITextBotStringResult) {
            return ((ITextBotStringResult) result).getText();
        }
        return "unhandled";
    }
}
