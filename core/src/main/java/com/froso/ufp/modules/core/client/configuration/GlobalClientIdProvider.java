package com.froso.ufp.modules.core.client.configuration;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created by ckleinhuis on 01.11.2017.
 */
public class GlobalClientIdProvider implements IGlobalsPropertyProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalClientIdProvider.class);
    @Autowired
    private IClientService clientService;
    @Autowired
    private IPropertyService propertyService;
    @Autowired
    private ClientManagerService clientManagerService;

    public Object getPropertyValue() {

        LOGGER.warn("HELLO " + clientService);
        String currentId = clientManagerService.getCurrentClientId();
        try {

            IClient client = ((IClient) clientService.findOne(currentId));

            return client.getId();
        } catch (Exception e) {

            return "no-client-found";
        }

    }

}
