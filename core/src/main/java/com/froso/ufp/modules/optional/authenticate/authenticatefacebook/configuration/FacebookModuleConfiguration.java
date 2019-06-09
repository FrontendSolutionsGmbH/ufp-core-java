package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.configuration;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(com.froso.ufp.core.annotations.UFPPublicController.class)},
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller",
                "com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service"
        })
public class FacebookModuleConfiguration {


    /**
     * Configure bean.
     *
     * @param globalsService the globals service
     */
    @Autowired(required = false)
    public void configureUfpFacebookBean(GlobalsService globalsService) {

        if (globalsService != null) {
            globalsService.addProperty("facebookAppId", FacebookAuthenticateCRUDServiceAbstract.PROP_NAME_FB_ID);
        }


    }

    /**
     * Configure bean.
     *
     * @param requestDefinitionService the request definition service
     */
    @Autowired(required = false)
    public void configureUfpFacebookBean(RequestDefinitionService requestDefinitionService) {
        requestDefinitionService.registerTokenFreePath("/" + FacebookAuthenticationConstants.PATH_AUTH);
        requestDefinitionService.registerTokenFreePath("/" + FacebookAuthenticationConstants.PATH_REG);
        // enter global properties here exposed in the globals ufp endpoint

    }

    /**
     * Configure bean.
     *
     * @param errorsService the errors service
     */
    @Autowired(required = false)
    public void configureUfpFacebook(ErrorsService errorsService) {
        if (errorsService != null) {
            errorsService.registerErrors(FacebookAuthenticateResultStatusEnumCode.class);

        }
    }
}
