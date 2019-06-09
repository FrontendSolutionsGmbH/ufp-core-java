package com.froso.ufp.modules.optional.websockets.controller;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.optional.websockets.model.*;
import io.swagger.annotations.*;
import java.text.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/" + Client.TYPE_NAME)
@Api(description = "Example public controller using a service",
        tags = "Websocket")
public class WebsocketController {
    @Autowired
    private SimpMessagingTemplate template;


    /**
     * Send message output.
     *
     * @param message the message
     * @return the message output
     */
    @MessageMapping("/*")
    @SendTo("/topic/messages")
    public MessageOutput send(Message message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new MessageOutput(message.getFrom(), message.getText(), time);
    }

    /**
     * Fire greeting.
     *
     * @param text the text
     */
    public void fireGreeting(String text) {
        this.template.convertAndSend("/topic/messages", text);
    }

    /**
     * Fire greeting.
     *
     * @param resourceName the resource name
     * @param id           the id
     * @param event        the event
     */
    public void fireGreeting2(String resourceName, String id, String event) {

        this.template.convertAndSend("/events/Resources", event + '|' + resourceName + "|" + id);
        this.template.convertAndSend("/events/Resources/" + resourceName, event + '|' + id);
        this.template.convertAndSend("/events/Resources/" + resourceName + "/" + id, event);
    }
}
