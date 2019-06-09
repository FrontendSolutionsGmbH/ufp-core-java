package com.froso.ufp.modules.optional.media.eventhandler;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.optional.media.model.*;
import org.slf4j.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 */
@Component
public class HandlerSaveMedia implements ApplicationListener<DataDocumentSaveEvent<Media>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerSaveMedia.class);

    public void onApplicationEvent(DataDocumentSaveEvent<Media> event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here
        Media user = event.getDataDocument();
        LOGGER.info("Media saved " + user.toString());


        // check if user shall be blocked, upon auth_email reset attempt, or auth_email fail


    }


}

