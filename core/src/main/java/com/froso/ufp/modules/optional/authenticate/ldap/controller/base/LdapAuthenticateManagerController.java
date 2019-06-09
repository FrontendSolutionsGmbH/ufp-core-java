package com.froso.ufp.modules.optional.authenticate.ldap.controller.base;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;
import com.froso.ufp.modules.optional.authenticate.ldap.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 15.11.13 Time: 14:58 To change this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = UFPAuthenticateConstants.AUTHENTICATIONS_PATH + "/" + LdapConstants.NAME)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class LdapAuthenticateManagerController extends AbstractAuthenticationManagerController<AuthenticateLdap, LdapAuthenticateDataBase, LdapAuthenticateDataBase, LdapAuthenticateDataBase> {


    @Autowired(required = false)
    private LdapRegisterService ldapRegisterService;

    @Override
    protected void transform(AuthenticateLdap current, LdapAuthenticateDataBase oldData) {

    }

    @Override
    protected AuthenticateLdap create(String coreUserId, LdapAuthenticateDataBase newData) {

        if (ldapRegisterService == null) {

            throw new UFPRuntimeException("EmailPassword registration not enabled");

        }
        throw new RuntimeException("Not yet implemented");
        //      return ldapRegisterService.registerAndReturnModel(coreUserService.findOneCoreUser(coreUserId), newData);
    }

}
