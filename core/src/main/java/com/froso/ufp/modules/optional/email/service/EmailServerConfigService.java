package com.froso.ufp.modules.optional.email.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.email.model.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:41 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
//@Service
public class EmailServerConfigService extends AbstractClientRefService<EmailServerConfig> {


    protected void fillDefaultObject(EmailServerConfig object) {
        // template method, sub classes may initialises their objects as they desire...
        object.setName("default");
    /*    object.setHost(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_HOST));
        object.setDirectory(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PATH));
        object.setPublicHttp(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PUBLICHTTP));
        object.setUserName(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_USERNAME));
        object.setUserPassword(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PASSWORD));
        object.setPort(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PORT));
*/
    }
}
