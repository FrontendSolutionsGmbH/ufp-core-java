package com.froso.ufp.modules.optional.authenticate.email.controller.auth;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.authenticate.email.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import javax.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = EmailAuthenticateConstants.PATH_AUTH)

@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION,
        tags = UFPAuthenticateConstants.AUTHENTICATION_TAG)
@UFPPublicController
public class EmailAuthenticateController {

    @Autowired
    private EmailAuthenticateService emailAuthenticateService;

    /**
     * Authenticate using telephone number response entity.
     *
     * @param loginData the login data
     * @param request   the request
     * @return the response entity
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "authenticate user via username/password",
            notes = "")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> authenticateUsingPasswordData(
            @RequestBody
            @Valid
            @ApiParam(required = true,
                    value = "username/password login credentials")
                    EmailAuthenticateDataAuthenticate loginData,
            BindingResult bindingResult,
            HttpServletRequest request) {
        ValidateBindingResult.validate(bindingResult);

        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        responseHandler.addResult(emailAuthenticateService.authenticate(loginData));
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/Nonce",
            method = RequestMethod.POST)
    @ApiOperation(value = "request a new nonce to be send to email",
            notes = "To perform a succsesful login using email and nonce a new nonce has to be created server side. By calling this endpoint a new nonce is generated and the user is informaed via email")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> requestNonce(
            @RequestBody
            @ApiParam(required = true)
                    EmailAuthenticateDataAuthenticateEmailOnly loginData, HttpServletRequest request) {

        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        emailAuthenticateService.requestNonce(loginData);
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

}
