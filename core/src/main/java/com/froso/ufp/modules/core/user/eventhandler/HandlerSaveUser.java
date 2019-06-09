package com.froso.ufp.modules.core.user.eventhandler;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.core.user.model.*;
import org.slf4j.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 */
@Component
public class HandlerSaveUser implements ApplicationListener<DataDocumentSaveEvent<CoreUser>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerSaveUser.class);

    /**
     * On application event.
     *
     * @param event the event
     */
    public void onApplicationEvent(DataDocumentSaveEvent<CoreUser> event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here

        LOGGER.info("SimpleUser saved " + event.getDataDocument().toString());

    }


}


