package com.froso.ufp.modules.optional.authenticate.email.controller.register;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.authenticate.email.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import javax.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

/**
 * The type CoreUser controller.
 *
 * @param <T> the type parameter
 * @author Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 11:15                  The SimpleUser Controller provides access to SimpleUser Management and Login.                  Remark:                  the "GET" to a userId is not implemented, the only way to retrieve a simpleUser-data object is by sending a         post to the login resource
 */
public class EmailGenericRegController<T extends ICoreUser> extends GenericRegController {

    @Autowired
    private AuthenticationsService authenticationsService;

    @Autowired
    private EmailRegisterService emailRegisterService;
    @Autowired
    private ResourcesService resourcesService;

    /**
     * Read element response entity.
     *
     * @param registrationEmail the registration email
     * @param bindingResult     the binding result
     * @param request           the request
     * @return the response entity
     */
    @ApiOperation(value = "Registers an account via email ",
            notes = "A registration via email is performed by sending an authorization code. The essential User Data is validated and obligatory fields need to be provided in the User field")
    @RequestMapping(
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode>> createAccountViaEmail(

            @RequestBody
            @Valid
                    GenericRegistration<EmailAuthenticateData, T> registrationEmail, BindingResult bindingResult
            , HttpServletRequest request) {
        ResponseHandlerTemplate2SingleObject<TokenData, EmailAuthResultStatusEnumCode> manager = new ResponseHandlerTemplate2SingleObject<>();
        ValidateBindingResult.validate(bindingResult);

        // binding seems to be ok, create new entry, and use the core user reference to create an account as well
        EmailAuthenticateModel pwmodel = emailRegisterService.registerPWDataAndReturnModel(registrationEmail.getData(), registrationEmail.getAuthentication());
        T account = registrationEmail.getData();
        if (account instanceof UFPUser) {
            UFPUser ufpUser = (UFPUser) account;

            ufpUser.getCoreUser().setId(pwmodel.getCoreUser().getId());
            AbstractRepositoryService2<UFPUser> service = resourcesService.getResourceServiceByClassName(this.getClassOfRepository());

            service.save(ufpUser);

            TokenData tokenData = authenticationsService.finalizeAuthorization((UFPUser) account);
            manager.addResult(tokenData);
            manager.setStatus(EmailAuthResultStatusEnumCode.NEW_USER_CREATED_VALIDATION_EMAIL_SEND);
        }
        //    manager.addResult(service.findByKeyValue("coreUser.id", token).get(0));
        return manager.getResponseEntity();
    }

}
