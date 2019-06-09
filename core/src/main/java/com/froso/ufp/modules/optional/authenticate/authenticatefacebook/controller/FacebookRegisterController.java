package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = FacebookAuthenticationConstants.PATH_REG)
@Api(description = UFPRegisterConstants.REGISTRATION_DESCRIPTION,
        tags = UFPRegisterConstants.TAG)
@UFPPublicController
public class FacebookRegisterController {


    @Autowired
    private FacebookRegisterService facebookRegisterService;

    /**
     * Authenticate with facebook access token response entity.
     *
     * @param loginData the login data
     * @param request   the request
     * @return the response entity
     * @throws Exception the exception
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "register user with facebook-access-token",
            notes = "Use an active facebook-access-token to create an account.Returns an ufp-session-token if autologin is enabled.")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, FacebookAuthenticateResultStatusEnumCode>> authenticateWithFacebookAccessToken(
            @RequestBody
            @ApiParam(required = true,
                    value = "Facebook Login Credentials")
                    FacebookAuthenticateData loginData, HttpServletRequest request) throws Exception

    {

        ResponseHandlerTemplate2SingleObject<TokenData, FacebookAuthenticateResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        TokenData model = facebookRegisterService.registerViaAccessTokenAndReturnTokenData(loginData.getAccessToken());
        responseHandler.addResult(model);

        responseHandler.setStatus(FacebookAuthenticateResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity();
    }


}
