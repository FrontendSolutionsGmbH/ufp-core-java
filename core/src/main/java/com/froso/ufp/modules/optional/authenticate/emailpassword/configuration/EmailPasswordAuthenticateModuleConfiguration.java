package com.froso.ufp.modules.optional.authenticate.emailpassword.configuration;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.emailpassword.controller.base",
                "com.froso.ufp.modules.optional.authenticate.emailpassword.service"
        })
public class EmailPasswordAuthenticateModuleConfiguration {

//    @Autowired(required = false)
//    public void configureBean(GlobalsService globalsService) {
//
//        // enter global properties here exposed in the globals ufp endpoint
//
//    }
//
//    @Bean
//    public EmailPasswordAuthenticateCRUDService getEmailPasswordAuthenticateCRUDService() {
//
//        // enter global properties here exposed in the globals ufp endpoint
//
//        return new EmailPasswordAuthenticateCRUDService();
//
//    }

    @Autowired(required = false)
    public void configureBean(RequestDefinitionService requestDefinitionService) {
        requestDefinitionService.registerTokenFreePath("/" + EmailPasswordConstants.PATH_AUTH);
        requestDefinitionService.registerTokenFreePath("/" + EmailPasswordConstants.PATH_AUTH + "/ResetPw");
        requestDefinitionService.registerTokenFreePath("/" + EmailPasswordConstants.PATH_REGISTER);
        // enter global properties here exposed in the globals ufp endpoint

    }
}
