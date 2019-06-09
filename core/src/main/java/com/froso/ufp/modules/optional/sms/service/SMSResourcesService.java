package com.froso.ufp.modules.optional.sms.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.sms.model.sms.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class SMSResourcesService extends AbstractResourcesService<ISMSProvider> {

    @Autowired
    public SMSResourcesService(ServiceService serviceService) {
        super(serviceService);
    }

    @Override
    public String getDescription() {
        return "Lists available SMS Providers";
    }

    @Override
    public String getName() {
        return "smsprovider";
    }

}
