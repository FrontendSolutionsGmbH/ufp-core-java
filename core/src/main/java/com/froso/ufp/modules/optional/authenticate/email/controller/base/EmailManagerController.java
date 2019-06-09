package com.froso.ufp.modules.optional.authenticate.email.controller.base;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.authenticate.email.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = UFPAuthenticateConstants.AUTHENTICATIONS_PATH + "/" + EmailAuthenticateConstants.PASSWORD_AUTH_NAME)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class EmailManagerController extends AbstractAuthenticationManagerController<EmailAuthenticateModel, EmailAuthenticateData, EmailAuthenticateDataPasswordOnly, EmailAuthenticateData> {
    @Autowired
    private EmailRegisterService emailRegisterService;

    @Override
    protected void transform(EmailAuthenticateModel current, EmailAuthenticateDataPasswordOnly oldData) {


    }

    @Override
    protected EmailAuthenticateModel create(String coreUserId, EmailAuthenticateData newData) {
        return emailRegisterService.createPWAuthenticationDirect(coreUserId, newData);
    }

    protected void validate(EmailAuthenticateModel emailAuthenticateModel, NonceData nonceData) {
        EmailEmailValidateData emailEmailValidateData = new EmailEmailValidateData();
        emailEmailValidateData.setAuthorizationID(emailAuthenticateModel.getId());
        emailEmailValidateData.setNonce(nonceData.getNonce());
        emailRegisterService.authorizeValidate(emailEmailValidateData);
    }
}
