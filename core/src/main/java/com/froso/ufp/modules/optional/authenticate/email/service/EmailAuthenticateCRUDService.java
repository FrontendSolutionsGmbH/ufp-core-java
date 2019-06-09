package com.froso.ufp.modules.optional.authenticate.email.service;

import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class EmailAuthenticateCRUDService extends AbstractCoreAuthenticationsService<EmailAuthenticateModel> {


    /**
     * Constructor Simple product service.
     */
    public EmailAuthenticateCRUDService() {

        super(EmailAuthenticateModel.TYPE_NAME);

    }

}
