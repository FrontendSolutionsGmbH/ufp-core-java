package com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.register;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping(value = SMSConstants.PATH_REGISTER)
@Api(tags = UFPRegisterConstants.TAG)
@UFPPublicController
public class SMSRegisterController {

    private final SMSRegisterService smsRegisterService;

    @Autowired
    public SMSRegisterController(SMSRegisterService smsRegisterService) {
        this.smsRegisterService = smsRegisterService;
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST)
    @ApiOperation(value = "register user via telephone number",
            notes = "The telephone number authentication/registration is always a 2-step  process. First step is to inform the backend to create a NONCE (number used once) for a valid telephone number. The second step is to enter the received nonce to finalize authentication/registration")

    @ResponseBody
    public ResponseEntity<BackendResponseTemplate2SingleObject<TokenData, SMSResultStatusEnumCode>> authenticateUsingTelephoneNumber(
            @RequestBody
            @ApiParam(required = true,
                    value = "SMS Login credentials")
                    SMSRegisterData loginData, HttpServletRequest request) {

        ResponseHandlerTemplate2SingleObject<TokenData, SMSResultStatusEnumCode> responseHandler = new ResponseHandlerTemplate2SingleObject<>(request);
        smsRegisterService.registerAndReturnModel(null, loginData);
        responseHandler.setStatus(SMSResultStatusEnumCode.OK);
        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

}
