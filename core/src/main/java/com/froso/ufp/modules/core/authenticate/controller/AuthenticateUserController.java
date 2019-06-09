package com.froso.ufp.modules.core.authenticate.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.session.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(UFPAuthenticateConstants.PATH)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class AuthenticateUserController {


    @Autowired
    private SessionService sessionService;
    @Autowired
    private AuthenticationsService authenticationsService;


    /**
     * Gets all associated authorizations.
     *
     * @param userId  the user id
     * @param request the request
     * @return the all associated authorizations
     */
    @ApiOperation(value = "get all associated authentications")

    @RequestMapping(value = "{token}/Account/" + UFPAuthenticateConstants.AUTHENTICATIONS,
            method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<AuthenticationsRequestResult>> getAllAssociatedAuthorizations(
            @PathVariable("token")
                    String userId, HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject<AuthenticationsRequestResult> manager = new ResponseHandlerTemplateSingleObject<>(request);


        AuthenticationsRequestResult result = new AuthenticationsRequestResult();
        result.setAuthentications(authenticationsService.getAllAuthenticationsForCoreUserAsShortRef(userId));
        manager.addResult(result);

        return manager.getResponseEntity();
    }

}
