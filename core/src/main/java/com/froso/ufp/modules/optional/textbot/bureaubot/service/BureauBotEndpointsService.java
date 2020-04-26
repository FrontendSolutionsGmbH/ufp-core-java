package com.froso.ufp.modules.optional.textbot.bureaubot.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.model.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class BureauBotEndpointsService extends AbstractRepositoryService2<BureauBotEndpoints> {

    /**
     * Constructor Simple product service.
     */
    public BureauBotEndpointsService() {

        super(BureauBot.TYPE_NAME);

    }

}
