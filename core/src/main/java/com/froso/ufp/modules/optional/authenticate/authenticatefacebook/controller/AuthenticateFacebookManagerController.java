package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import facebook4j.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = UFPAuthenticateConstants.AUTHENTICATIONS_PATH + "/" + FacebookAuthenticationConstants.NAME)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class AuthenticateFacebookManagerController extends AbstractAuthenticationManagerController<FacebookModel, FacebookData, FacebookAuthenticateData, FacebookAuthenticateData> {

    @Autowired
    private FacebookRegisterService facebookRegisterService;

    @Override
    protected void transform(FacebookModel current, FacebookAuthenticateData oldData) {
        try {
            Facebook facebook = facebookRegisterService.getFacebookForAccessToken(oldData.getAccessToken());

            if (facebook != null) {

                current.getData().setFacebookId(facebook.getMe().getId());
            }
        } catch (Exception e) {
            throw new FacebookRegisterException.InvalidOAth();
        }
    }

    @Override
    protected FacebookModel create(String coreUserId, FacebookAuthenticateData newData) {
        //     return emailRegisterService.createPWAuthenticationDirect(coreUserId, newData);

        return facebookRegisterService.createWithExistingCoreUser(coreUserId, newData.getAccessToken());
    }

}
