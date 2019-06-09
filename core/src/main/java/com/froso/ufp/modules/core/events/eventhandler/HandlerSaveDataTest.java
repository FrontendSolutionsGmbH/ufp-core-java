package com.froso.ufp.modules.core.events.eventhandler;

import com.froso.ufp.core.domain.events.*;
import org.slf4j.*;
import org.springframework.context.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 */
// @Component
public class HandlerSaveDataTest implements ApplicationListener<DataDocumentSaveEvent<?>> {

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
    public void onApplicationEvent(DataDocumentSaveEvent<?> event) {
        // until spring4.2 we cannot use generic typed events, so we need to check ourselves here

        LOGGER.info("Element saved " + event.getDataDocument().toString());


        // check if user shall be blocked, upon auth_email reset attempt, or auth_email fail


    }


}


