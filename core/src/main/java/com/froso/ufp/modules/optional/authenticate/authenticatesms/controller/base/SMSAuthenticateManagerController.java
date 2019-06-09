package com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.base;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.service.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = UFPAuthenticateConstants.AUTHENTICATIONS_PATH + "/" + SMSConstants.NAME)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class SMSAuthenticateManagerController extends AbstractAuthenticationManagerController<SMSAuthenticateModel, SMSAuthenticateData, SMSAuthenticateData, SMSRegisterData> {

    private final SMSRegisterService smsRegisterService;
    private final ICoreUserService coreUserService;

    @Autowired
    public SMSAuthenticateManagerController(SMSRegisterService smsRegisterService, ICoreUserService coreUserService) {
        this.smsRegisterService = smsRegisterService;
        this.coreUserService = coreUserService;
    }

    @Override
    protected void transform(SMSAuthenticateModel current, SMSAuthenticateData oldData) {

        current.getData().setPhoneNumber(smsRegisterService.validatePhoneNumberAndReturnCleaned(oldData.getPhoneNumber()));
        // remove any existiong nonces
        current.getData().setNonceData(null);
        smsRegisterService.sendWelcomeSMS(current);
    }

    @Override
    protected SMSAuthenticateModel create(String coreUserId, SMSRegisterData newData) {

        return smsRegisterService.registerAndReturnModel(coreUserService.findOneCoreUser(coreUserId), newData);
    }

}
