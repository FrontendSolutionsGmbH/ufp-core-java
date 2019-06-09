package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.events;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import org.slf4j.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 */
@Component
public class HandlerSaveDataTest implements ApplicationListener<DataDocumentSaveEvent<FacebookModel>> {

    /**
     * The constant PROP_NAME_USERSERVICE_MAXLOGINRETRIES.
     */
    public static final String PROP_NAME_USERSERVICE_MAXLOGINRETRIES = "security.counters.max.login";
    /**
     * The constant PROP_NAME_USERSERVICE_MAXPWRESET.
     */
    public static final String PROP_NAME_USERSERVICE_MAXPWRESET = "security.counters.max.passwordreset";
    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerSaveDataTest.class);

    /**
     * On application event.
     *
     * @param event the event
     */
    public void onApplicationEvent(DataDocumentSaveEvent<FacebookModel> event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here

        LOGGER.info("Media saved " + event.getDataDocument().toString());


        // check if user shall be blocked, upon auth_email reset attempt, or auth_email fail


    }


}


