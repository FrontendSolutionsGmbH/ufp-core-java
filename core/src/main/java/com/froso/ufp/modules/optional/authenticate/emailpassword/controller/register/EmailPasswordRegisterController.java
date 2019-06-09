package com.froso.ufp.modules.optional.authenticate.emailpassword.controller.register;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.register.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 *
 * @param <T> the type parameter
 */
@RequestMapping(value = EmailPasswordConstants.PATH_REGISTER)
@Api(tags = UFPRegisterConstants.TAG)
public class EmailPasswordRegisterController<T extends ICoreUser> {

    private final EmailPasswordRegisterService usernamePasswordRegisterService;

    @Autowired
    public EmailPasswordRegisterController(EmailPasswordRegisterService usernamePasswordRegisterService) {
        this.usernamePasswordRegisterService = usernamePasswordRegisterService;
    }

    /**
     * Authenticate using telephone number response entity.
     *
     * @param registrationEmail the registration email
     * @param bindingResult     the binding result
     * @param request           the request
     * @return the response entity
     */
    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "register user via username/password number",
            notes = "The telephone number authentication/registration is always a 2-step  process. First step is to inform the backend to create a NONCE (number used once) for a valid telephone number. The second step is to enter the received nonce to finalize authentication/registration")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, EmailPasswordResultStatusEnumCode>> authenticateUsingTelephoneNumber(

            @ApiParam(required = true,
                    value = "UsernamePassword Login credentials")
            @RequestBody
            @Valid
                    GenericRegistration<EmailPasswordRegisterData, T> registrationEmail, BindingResult bindingResult
            , HttpServletRequest request) {
        ValidateBindingResult.validate(bindingResult);

        ResponseHandlerTemplate2SingleObject<TokenData, EmailPasswordResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        usernamePasswordRegisterService.registerAndReturnModel(registrationEmail.getData(), registrationEmail.getAuthentication());
        responseHandler.setStatus(EmailPasswordResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

}
