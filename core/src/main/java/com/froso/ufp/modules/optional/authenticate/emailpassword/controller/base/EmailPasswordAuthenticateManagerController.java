package com.froso.ufp.modules.optional.authenticate.emailpassword.controller.base;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = UFPAuthenticateConstants.AUTHENTICATIONS_PATH + "/" + EmailPasswordConstants.NAME)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class EmailPasswordAuthenticateManagerController extends AbstractAuthenticationManagerController<AuthenticateEmailPassword, EmailPasswordAuthenticateData, EmailPasswordAuthenticateData, EmailPasswordRegisterData> {

    private final EmailPasswordRegisterService usernamePasswordRegisterService;
    private final ICoreUserService coreUserService;

    @Autowired
    public EmailPasswordAuthenticateManagerController(EmailPasswordRegisterService usernamePasswordRegisterService, ICoreUserService coreUserService) {
        this.usernamePasswordRegisterService = usernamePasswordRegisterService;
        this.coreUserService = coreUserService;
    }

    @Override
    protected void transform(AuthenticateEmailPassword current, EmailPasswordAuthenticateData oldData) {

        if (usernamePasswordRegisterService == null) {

            throw new UFPRuntimeException("EmailPassword registration not enabled");

        }
        current.getData().setPassword(oldData.getPassword());
        usernamePasswordRegisterService.sendWelcomeUsernamePassword(current);
    }

    @Override
    protected AuthenticateEmailPassword create(String coreUserId, EmailPasswordRegisterData newData) {

        if (usernamePasswordRegisterService == null) {

            throw new UFPRuntimeException("EmailPassword registration not enabled");

        }

        return usernamePasswordRegisterService.registerAndReturnModel(coreUserService.findOneCoreUser(coreUserId), newData);
    }

}
