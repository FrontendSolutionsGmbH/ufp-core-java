package com.froso.ufp.modules.optional.authenticate.email.controller.register;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.authenticate.email.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = EmailAuthenticateConstants.PATH_REGISTER)
@Api(
        tags = UFPRegisterConstants.TAG)
@UFPPublicController
public class EmailRegisterController {

    @Autowired
    private EmailRegisterService emailRegisterService;


    /**
     * Authenticate using telephone number response entity.
     *
     * @param loginData the login data
     * @param request   the request
     * @return the response entity
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "register user via email",
            notes = "Sending an email to this endpoint creates an authentication based on email. A password (nonce) has to be created for each login attempt.")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> authenticateUsingPasswordData(
            @RequestBody
            @ApiParam(required = true,
                    value = "username/password login credentials")
                    EmailAuthenticateData loginData, HttpServletRequest request) {

        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        responseHandler.addResult(emailRegisterService.registerPWData(loginData));
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }
/*
    @RequestMapping(value = "/PasswordForgot",
            method = RequestMethod.POST)
    @ApiOperation(value = "password reminder",
            notes = "to start the password forgot process provide a username or email associated with the authentication to reset")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> register(
            @RequestBody
            @ApiParam(required = true,
                    value = "email/username to start password forgot process for")
            PasswordAuthenticateData userNameData, HttpServletRequest request) throws Exception

    {

        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        //responseHandler.addResult(passwordRegisterService.registerPWData(userNameData));
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/{authId}/{nonce}",
            method = RequestMethod.GET)
    @ApiOperation(value = "HTML auth validation entry", produces = "text/html",
            notes = "This endpoint should never be called directly. Registration Emails link to this endpoint to finish validation of email.")

    public ResponseEntity<byte[]> validateEmail(
            @PathVariable String authId, @PathVariable String nonce
            , HttpServletRequest request) throws Exception {
        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("auth_email");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

// obtain authorization
        // todo move into service

        String templateName = "";
        // Get the specific Category from db
        // response.setContentType(ContentType.TEXT_HTML.toString());
        EmailAuthenticateModel passwordAuthenticateModel = emailAuthenticateCRUDService.findOneBrute(authId);
        if (passwordAuthenticateModel == null) {
            templateName = "html-content-account-invalid.vm";


        } else {
            // check if nonce matches
            if (nonce != null && nonce.equals(passwordAuthenticateModel.getdata().getUsername().getUsername())) {

                templateName = "html-content-verify-account.vm";
            } else {

                templateName = "html-content-account-invalidnonce.vm";
            }
        }

        byte[] result = templateService.parseTemplateBytes("Default", templateName, new HashMap<String, Object>(), settings);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/{authId}/{nonce}",
            method = RequestMethod.POST)
    @ApiOperation(value = "HTML auth validation response",
            notes = "")


    public ResponseEntity<byte[]> validateEmailFinish(
            @PathVariable String authId, @PathVariable String nonce
            , HttpServletRequest request) throws Exception

    {
        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("auth_email");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);


        // todo move into service

        String templateName = "";
        // Get the specific Category from db
        // response.setContentType(ContentType.TEXT_HTML.toString());
        EmailAuthenticateModel passwordAuthenticateModel = emailAuthenticateCRUDService.findOneBrute(authId);
        if (passwordAuthenticateModel == null) {
            templateName = "html-content-account-invalid.vm";


        } else {
            // check if nonce matches
            if (nonce != null && nonce.equals(passwordAuthenticateModel.getdata().getUsername().getUsername())) {
// finish email validation

                EmailEmailValidateData passwordEmailValidateData = new EmailEmailValidateData();
                passwordEmailValidateData.setAuthorizationID(authId);
                passwordEmailValidateData.setUsername(nonce);
                emailRegisterService.authorizeValidate(passwordEmailValidateData);
                templateName = "html-content-account-verified.vm";
            } else {

                templateName = "html-content-account-invalidnonce.vm";
            }
        }

        byte[] result = templateService.parseTemplateBytes("Default", templateName, new HashMap<String, Object>(), settings);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);

    }


    @RequestMapping(value = "/Validate",
            method = RequestMethod.POST)
    @ApiOperation(value = "validate an email",
            notes = "This endpoint may be used to validate an email, authorization id is required and the current belonging nonce.")


    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> validateEmailFinishEndpoint(@RequestBody EmailEmailValidateData passwordEmailValidateData,
                                                                                                                                         HttpServletRequest request) throws Exception

    {


        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        emailRegisterService.authorizeValidate(passwordEmailValidateData);
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);

    }*/
}
