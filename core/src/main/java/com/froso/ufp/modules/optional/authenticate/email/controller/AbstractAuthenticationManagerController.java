package com.froso.ufp.modules.optional.authenticate.email.controller;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Api(
        tags = UFPAuthenticateConstants.AUTHENTICATION)
abstract public class AbstractAuthenticationManagerController<T extends AbstractAuthentication<DATA_OBJECT>, DATA_OBJECT extends IDataObject, UPDATE_OBJECT extends IDataObject, CREATE_OBJECT extends IDataObject> {

    @Autowired
    private AbstractCoreAuthenticationsService<T> authenticateCRUDService;

    abstract protected void transform(T current, UPDATE_OBJECT data);


    abstract protected T create(String coreUserId, CREATE_OBJECT newData);

    @RequestMapping(value = "/{authenticationId}",
            method = RequestMethod.POST)
    @ApiOperation(value = "update an authentication",
            notes = "This endpoint allows to change an authorization. It is accessed through the concrete id of an authorization, obtained")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<DATA_OBJECT, EmailAuthResultStatusEnumCode>> authenticateUsingPasswordData(
            @RequestBody
            @ApiParam(required = true,
                    value = "Updates an authorization, depending on the authorization validation processes are triggerred automatically")
                    UPDATE_OBJECT authData,

            @PathVariable("token") String userId,

            @PathVariable("authenticationId") String authenticationId,
            HttpServletRequest request) {

        ResponseHandlerTemplate2SingleObject<DATA_OBJECT, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        //     responseHandler.addResult(passwordRegisterService.registerPWData(loginData));


        T authorization = authenticateCRUDService.findOne(authenticationId);

        transform(authorization, authData);

        authenticateCRUDService.save(authorization);
        responseHandler.addResult(authorization.getData());
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "create a new authentication of this type",
            notes = "")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> createNewAuthorization(
            @RequestBody
            @ApiParam(required = true,
                    value = "creates a new authentication")
                    CREATE_OBJECT authData,

            @PathVariable("token") String userId,

            HttpServletRequest request) {

        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        //     responseHandler.addResult(passwordRegisterService.registerPWData(loginData));
        authenticateCRUDService.save(create(userId, authData));
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{authenticationId}",
            method = RequestMethod.GET)
    @ApiOperation(value = "retrieves the authentication",
            notes = "to start the password forgot process provide a username or email associated with the authentication to reset")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<DATA_OBJECT, EmailAuthResultStatusEnumCode>> register(
            @PathVariable("token") String userId,
            @PathVariable("authenticationId") String authenticationId,
            HttpServletRequest request) {

        ResponseHandlerTemplate2SingleObject<DATA_OBJECT, EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);

        responseHandler.addResult(authenticateCRUDService.findOne(authenticationId).getData());
        responseHandler.setStatus(EmailAuthResultStatusEnumCode.OK);
        //responseHandler.addResult(passwordRegisterService.registerPWData(userNameData));
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/{authenticationId}",
            method = RequestMethod.DELETE)
    @ApiOperation(value = "delete the authorization",
            notes = "Deletes a single authorization, validation process triggered automatically")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2Empty<EmailAuthResultStatusEnumCode>> validateEmail(
            @PathVariable("token") String userId,

            @PathVariable("authenticationId") String authenticationId
            , HttpServletRequest request) {

        ResponseHandlerTemplate2Empty<EmailAuthResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2Empty<>();


        T auth = authenticateCRUDService.findOne(authenticationId);
        if (auth != null) {
            authenticateCRUDService.delete(auth);
        }
        //responseHandler.addResult(passwordRegisterService.registerPWData(userNameData));
        return responseHandler.getResponseEntity(HttpStatus.OK);

    }


    @RequestMapping(value = "/{authenticationId}/Status",
            method = RequestMethod.POST)
    @ApiOperation(value = "disable/enable authorization",
            notes = "")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplateEmpty> setAuthorizationStatus(
            @PathVariable("token") String userId,

            @RequestBody AuthenticationStatusImpl status,
            @PathVariable("authenticationId") String authenticationId
            , HttpServletRequest request) {
        ResponseHandlerTemplateEmpty responseHandler = new ResponseHandlerTemplateEmpty();
        T authorization = authenticateCRUDService.findOne(authenticationId);
        authorization.setEnabled(status.isEnabled());
        authenticateCRUDService.save(authorization);
        return responseHandler.getResponseEntity();

    }


}
