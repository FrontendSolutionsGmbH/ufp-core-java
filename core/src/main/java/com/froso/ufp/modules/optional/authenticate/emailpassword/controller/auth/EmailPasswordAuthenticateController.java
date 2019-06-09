package com.froso.ufp.modules.optional.authenticate.emailpassword.controller.auth;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;

@Controller
@RequestMapping(value = EmailPasswordConstants.PATH_AUTH)
@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION,
        tags = UFPAuthenticateConstants.AUTHENTICATION_TAG)
@UFPPublicController
public class EmailPasswordAuthenticateController {

    private final EmailPasswordAuthenticateService emailPasswordAuthenticateService;

    @Autowired
    public EmailPasswordAuthenticateController(EmailPasswordAuthenticateService emailPasswordAuthenticateService) {
        this.emailPasswordAuthenticateService = emailPasswordAuthenticateService;
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "authenticate/register user via username/password number",
            notes = "enter your credentials")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailPasswordResultStatusEnumCode>> authenticate(
            @RequestBody
            @ApiParam(required = true,
                    value = "UsernamePassword Login credentials")
            @Valid EmailPasswordAuthenticateRequestData loginData, BindingResult bindingResult, HttpServletRequest request) {

        ValidateBindingResult.validate(bindingResult);

        ResponseHandlerTemplate2SingleObject<TokenData, EmailPasswordResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        TokenData tokenData = emailPasswordAuthenticateService.authenticateUsernamePasswordData(loginData);
        responseHandler.addResult(tokenData);
        responseHandler.setStatus(EmailPasswordResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/Nonce/{nonceValue}",
            method = RequestMethod.GET)
    @ApiOperation(value = "verify that a nonce is existant",
            notes = "to be used in the process of password reset, a password reset triggers the creation of a Nonce, this nonce has to be valid and if not valid the reset process has to inform the user accordingly ")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2Empty<EmailPasswordResultStatusEnumCode>> validateNonce(
            @ApiParam("The Nonce Value to check")
            @PathVariable String nonceValue,
            HttpServletRequest request) {

        ResponseHandlerTemplate2Empty<EmailPasswordResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2Empty<>(request);

        if (emailPasswordAuthenticateService.validateNonce(nonceValue)) {
            responseHandler.setStatus(EmailPasswordResultStatusEnumCode.OK);
        } else {
            responseHandler.setStatus(EmailPasswordResultStatusEnumCode.UsernamePassword_NONCE_INVALID);

        }

        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/Nonce/{nonceValue}",
            method = RequestMethod.POST)
    @ApiOperation(value = "change emailpassword password",
            notes = "to be used in the process of password reset, a password reset triggers the creation of a Nonce, this nonce has to be valid and if not valid the reset process has to inform the user accordingly ")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailPasswordResultStatusEnumCode>> postNewPassword(
            @ApiParam("The Nonce Value to check")
            @PathVariable String nonceValue,

            @RequestBody EmailPasswordAuthenticateRequestDataPasswordOnly data
            , BindingResult bindingResult, HttpServletRequest request) {

        ValidateBindingResult.validate(bindingResult);

        ResponseHandlerTemplate2SingleObject<TokenData, EmailPasswordResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);

        if (emailPasswordAuthenticateService.validateNonce(nonceValue)) {
            emailPasswordAuthenticateService.setNewPasswordViaNonce(nonceValue, data);
            responseHandler.setStatus(EmailPasswordResultStatusEnumCode.OK);

        } else {
            responseHandler.setStatus(EmailPasswordResultStatusEnumCode.UsernamePassword_NONCE_INVALID);
        }

        // nonce seems to be valid update password
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/Nonce",
            method = RequestMethod.POST)
    @ApiOperation(value = "request a password reset",
            notes = "the server will send out an email ")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2Empty<EmailPasswordResultStatusEnumCode>> requestNewNonce(
            @RequestBody
            @ApiParam(required = true,
                    value = "UsernamePassword Login credentials")
            @Valid
                    EmailPasswordAuthenticateRequestResponse loginData, BindingResult bindingResult, HttpServletRequest request) {

        ValidateBindingResult.validate(bindingResult);

        ResponseHandlerTemplate2Empty<EmailPasswordResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2Empty<>(request);
        emailPasswordAuthenticateService.createNewNonceForEmail(loginData);
        //  responseHandler.addResult(tokenData);
        //   responseHandler.setStatus(UsernamePasswordResultStatusEnumCode.OK);
        responseHandler.setStatus(EmailPasswordResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }
}
