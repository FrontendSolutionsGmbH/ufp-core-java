package com.froso.ufp.modules.optional.websockets.eventhandler;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.optional.websockets.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

/**
 * event handler for receiving post_update crud events, and firing them into the websocket stream to connected clients
 */
@Component
public class HandlerWebsocketDatadocumentEvent implements ApplicationListener<DataDocumentEvent> {
    @Autowired
    private WebsocketController websocketController;

    public void onApplicationEvent(DataDocumentEvent event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here

        // LOGGER.info("Media changed   " + event.getDataDocument().toString());
        if (event.getEventName() != DataDocumentCRUDEvent.POST_UPDATE) {
            //      websocketController.fireGreeting(event.getEventName().toString() + "|" + event.getDataDocument().getMetaData().getType() + "|" + event.getDataDocument().getId());
            websocketController.fireGreeting2(event.getDataDocument().getMetaData().getType(), event.getDataDocument().getId(), event.getEventName().toString());
        }

    }

}


