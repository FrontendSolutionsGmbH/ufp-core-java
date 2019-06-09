package com.froso.ufp.modules.optional.authenticate.authenticatesms.configuration;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.base",
                "com.froso.ufp.modules.optional.authenticate.authenticatesms.service"
        })
public class SMSAuthenticateModuleConfiguration {



    @Autowired(required = false)
    public void configureUfpSms(RequestDefinitionService requestDefinitionService) {
        requestDefinitionService.registerTokenFreePath("/" + SMSConstants.PATH_AUTH);
        requestDefinitionService.registerTokenFreePath("/" + SMSConstants.PATH_AUTH + "/Nonce");
        requestDefinitionService.registerTokenFreePath("/" + SMSConstants.PATH_REGISTER);
        // enter global properties here exposed in the globals ufp endpoint

    }


}
