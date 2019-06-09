package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = FacebookAuthenticationConstants.PATH_AUTH)
@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION,
        tags = UFPAuthenticateConstants.AUTHENTICATION_TAG)
@UFPPublicController
public class FacebookAuthenticateController {

    /**
     * Instantiates a new Client controller.
     *
     * @param service the service
     */
    /*
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookAuthenticateController.class);
    @Autowired
    FacebookService facebookService;

    @RequestMapping(value = "Authenticate/{accessToken}",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponse> getElements(@PathVariable String accessToken, HttpServletRequest request) throws Exception {

        ResponseHandler responseHandler = new ResponseHandler(request);
        // responseHandler.addResult(service.findAll());
        SSLConnectionSocketFactory factory;
        //  Facebook authenticatefacebook = new FacebookTemplate(accessToken);
        // LOGGER.info("Facebnook is ; " + authenticatefacebook.userOperations().getUserProfile().toString());
        Facebook authenticatefacebook = facebookService.getFacebookForAccessToken(accessToken);
        LOGGER.info("Facebnook is ; " + authenticatefacebook.users().getMe().toString());


        responseHandler.addResult(authenticatefacebook.users().getMe());

        return responseHandler.getResponseEntity();
    }
    */


    @Autowired
    private FacebookAuthenticateService facebookService;
    @Autowired
    private AuthenticationsService authenticationsService;

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
    @ApiOperation(value = "authenticate user with facebook-access-token",
            notes = "Use an active facebook-access-token to authenticate an already associated returns an ufp-session-token")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, FacebookAuthenticateResultStatusEnumCode>> authenticateWithFacebookAccessToken(
            @RequestBody
            @ApiParam(required = true,
                    value = "Facebook Login Credentials")
                    FacebookAuthenticateData loginData, HttpServletRequest request) throws Exception

    {

        ResponseHandlerTemplate2SingleObject<TokenData, FacebookAuthenticateResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        FacebookModel model = facebookService.getEntryForAccessToken(loginData.getAccessToken());
        responseHandler.addResult(authenticationsService.finalizeAuthorization(model));

        responseHandler.setStatus(FacebookAuthenticateResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity();
    }


}
