package com.froso.ufp.modules.core.client.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.client.model.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:41 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
//@Service
public class ClientService extends AbstractRepositoryService2<Client> {

    /**
     * The constant PROP_NAME_CLIENT_ID.
     */
    public static final String PROP_NAME_CLIENT_ID = "ufp.modules.core.client.defaultClientId";

    @Autowired
    protected IPropertyService propertyService;

    /**
     * Constructor Simple client service.
     */
    public ClientService() {

        super(Client.TYPE_NAME);
    }

    @Override
    protected void fillDefaultObject(Client object) {

        // create first client with default id if not present
        if (findOneBrute(propertyService.getPropertyValue(PROP_NAME_CLIENT_ID)) == null) {
            object.setId(propertyService.getPropertyValue(PROP_NAME_CLIENT_ID));
        }

    }

}
