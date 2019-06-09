package com.froso.ufp.modules.core.client.service;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:41 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class ClientManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientManagerService.class);
    @Autowired
    private IPropertyService propertyService;

    /**
     * utility method to obtain the current active client id, if inside request possible to set via header or various other ways
     *
     * @return current client id
     */
    public String getCurrentClientId() {
        String result ;
        String requestId = null;
        try {
            requestId = RequestHeaderRetriever.getCurrentClient();

        } catch (Exception e) {
            LOGGER.error("Clientmanager retrieve from request" + e);

        }

        if (null == requestId||RequestHeaderRetriever.NOTSET==requestId) {
            result = getDefaultClientId();
        } else {
            result = requestId;
        }

        //LOGGER.debug("Clientmanager returning clientid " + result);
        return result;

    }

    /**
     * Gets default client id.
     *
     * @return the default client id
     */
    public String getDefaultClientId() {

        return propertyService.getPropertyValue(ClientService.PROP_NAME_CLIENT_ID);

    }

}
