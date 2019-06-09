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
@ComponentScan(
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = {FacebookAuthenticateController.class})},
        basePackages = {
                "com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller",
                "com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service"
        })
public class FacebookModuleConfigurationAuthenticatePublic {


    /**
     * Configure bean.
     *
     * @param globalsService the globals service
     */
    @Autowired(required = false)
    public void configureFacebookPublic(GlobalsService globalsService) {

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
    public void configureFacebookPublic(RequestDefinitionService requestDefinitionService) {
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
    public void configureBean(ErrorsService errorsService) {
        if (errorsService != null) {
            errorsService.registerErrors(FacebookAuthenticateResultStatusEnumCode.class);

        }
    }
}
