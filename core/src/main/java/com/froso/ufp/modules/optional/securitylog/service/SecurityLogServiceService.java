package com.froso.ufp.modules.optional.securitylog.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.securitylog.interfaces.*;
import com.froso.ufp.modules.optional.securitylog.model.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:51 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 * <p>
 * all crud method available provided by this service
 */
@Service
public class SecurityLogServiceService extends AbstractRepositoryService2<SecurityLog> implements ISecurityLogService {


    /**
     * Instantiates a new Simple security log service service.
     */
    public SecurityLogServiceService() {

        super(SecurityLog.TYPE_NAME);

    }


}
