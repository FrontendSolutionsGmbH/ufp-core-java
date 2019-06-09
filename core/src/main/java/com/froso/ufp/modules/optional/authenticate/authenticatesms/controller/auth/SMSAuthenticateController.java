package com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.auth;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping(value = SMSConstants.PATH_AUTH)
@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION,
        tags = UFPAuthenticateConstants.AUTHENTICATION_TAG)
@UFPPublicController
public class SMSAuthenticateController {

    public static final String TEMPLATE_SMS_NONCE_INVALID = "|TEMPLATE_SMS_NONCE_INVALID|When the provided nonce does not match the  current one in database|\n";
    public static final String TEMPLATE_SMS_NONCE_EXPIRED = "|SMS_NONCE_EXPIRED|When the max time is over for the nonce since creation|\n";
    public static final String SMS_NONCE_NOT_INITIALIZED = "|SMS_NONCE_NOT_INITIALIZED|When there exists no stored nonce to check against|\n";
    public static final String SMS_PHONENUMBER_UNKNOWN = "|SMS_PHONENUMBER_UNKNOWN|If the phonenumber is not known to the system |\n";
    public static final String TEMPLATE_INVALID_PHONE_NUMBER = "|SMS_INVALID_PHONE_NUMBER|If the phonenumber has any syntactical errors |\n";
    public static final String TEMPLATE_SMS_AUTHORIZATION_DISABLED = "|SMS_AUTHORIZATION_DISABLED|When the particular authentication associated with the - already known - phone number is disabled and cannot be used anymore|\n";

    private final SMSAuthenticateService smsAuthenticateService;

    @Autowired
    public SMSAuthenticateController(SMSAuthenticateService smsAuthenticateService) {
        this.smsAuthenticateService = smsAuthenticateService;
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "authenticate/register user via telephone number",
            notes = "The telephone number authentication/registration is always a 2-step  process. \n\nFirst step is to inform the backend to create a NONCE (number used once) for a valid telephone number. \n\nThe second step is to enter the received nonce to finalize authentication/registration" +

                    SwaggerDocSnippets.RESPONSE_START +
                    TEMPLATE_INVALID_PHONE_NUMBER +
                    TEMPLATE_SMS_NONCE_INVALID +
                    TEMPLATE_SMS_NONCE_EXPIRED +
                    SMS_NONCE_NOT_INITIALIZED +
                    SMS_PHONENUMBER_UNKNOWN +
                    TEMPLATE_SMS_AUTHORIZATION_DISABLED +
                    SwaggerDocSnippets.RESPONSE_END)

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate3SingleObject<TokenData, SmsResponseStatusTyped>> authenticateUsingTelephoneNumber(
            @RequestBody
            @ApiParam(required = true,
                    value = "SMS Login credentials")
                    SMSAuthenticateRequestData loginData, HttpServletRequest request) {

        ResponseHandlerTemplate3SingleObject<TokenData, SmsResponseStatusTyped> responseHandler = new ResponseHandlerTemplate3SingleObject<>(request, new SmsResponseStatusTyped());
        TokenData tokenData = smsAuthenticateService.authenticateSMSData(loginData);
        responseHandler.addResult(tokenData);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/Nonce",
            method = RequestMethod.POST)
    @ApiOperation(value = "request sending of nonce to phone number",
            notes = "<p>If incoming telephone number is known by the system a sms with the information about the current nonce value will be send to the phone number</p>" +

                    SwaggerDocSnippets.RESPONSE_START +
                    TEMPLATE_INVALID_PHONE_NUMBER +
                    TEMPLATE_SMS_NONCE_INVALID +
                    TEMPLATE_SMS_NONCE_EXPIRED +
                    SMS_NONCE_NOT_INITIALIZED +
                    SMS_PHONENUMBER_UNKNOWN +
                    TEMPLATE_SMS_AUTHORIZATION_DISABLED +
                    SwaggerDocSnippets.RESPONSE_END)

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate3Empty<SmsResponseStatusTyped>> requestNewNonce(
            @RequestBody
            @ApiParam(required = true,
                    value = "SMS Login credentials")
                    SMSAuthenticateRequestDataPhonenumberOnly loginData, HttpServletRequest request) {

        ResponseHandlerTemplate3Empty responseHandler = new ResponseHandlerTemplate3Empty(request, new SmsResponseStatusTyped());
        smsAuthenticateService.createNewNonceForPhoneNumber(loginData);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }
}
