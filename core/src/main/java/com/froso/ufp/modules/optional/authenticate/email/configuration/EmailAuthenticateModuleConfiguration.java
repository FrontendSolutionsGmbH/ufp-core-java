package com.froso.ufp.modules.optional.authenticate.email.configuration;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.email.controller.base",
                "com.froso.ufp.modules.optional.authenticate.email.service"
        })
public class EmailAuthenticateModuleConfiguration {


    @Autowired(required = false)
    public void configureUfpEmailAuth(RequestDefinitionService requestDefinitionService) {
        requestDefinitionService.registerTokenFreePath("/" + EmailAuthenticateConstants.PATH_REGISTER);
        requestDefinitionService.registerTokenFreePath("/" + EmailAuthenticateConstants.PATH_AUTH);
        // enter global properties here exposed in the globals ufp endpoint

    }


}
