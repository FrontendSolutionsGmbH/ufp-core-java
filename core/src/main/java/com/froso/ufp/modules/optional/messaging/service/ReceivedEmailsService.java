package com.froso.ufp.modules.optional.messaging.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.messaging.model.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:41 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
//@Service
public class ReceivedEmailsService extends AbstractRepositoryService2<ReceivedEmail> {

    /**
     * Instantiates a new Simple email service.
     */
    public ReceivedEmailsService() {
        super(ReceivedEmail.TYPE_NAME);
    }
}
