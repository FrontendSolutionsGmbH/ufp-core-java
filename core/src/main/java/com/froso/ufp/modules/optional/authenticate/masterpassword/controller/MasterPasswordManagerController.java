package com.froso.ufp.modules.optional.authenticate.masterpassword.controller;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.service.*;
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
@RequestMapping(value = UFPAuthenticateConstants.AUTHENTICATIONS_PATH + "/" + MasterPasswordConstants.NAME)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class MasterPasswordManagerController extends AbstractAuthenticationManagerController<MasterPasswordModel, MasterPasswordAuthenticateData, MasterPasswordAuthenticateData, MasterPasswordAuthenticateData> {

    @Autowired
    private MasterPasswordAuthenticateService masterPasswordAuthenticateService;
    @Autowired
    private MasterPasswordAuthenticateCRUDService masterPasswordAuthenticateCRUDService;

    @Override
    protected void transform(MasterPasswordModel current, MasterPasswordAuthenticateData oldData) {
        current.getData().setPassword(masterPasswordAuthenticateService.getTransformedAndEncryptedPassword(oldData.getPassword()));

    }

    @Override
    protected MasterPasswordModel create(String coreUserId, MasterPasswordAuthenticateData newData) {
        //retur// ma.createPWAuthenticationDirect(coreUserId, newData);
        MasterPasswordModel masterPasswordModel = masterPasswordAuthenticateCRUDService.createNewDefault();
        masterPasswordModel.getCoreUser().setId(coreUserId);

        masterPasswordModel.getData().setPassword(masterPasswordAuthenticateService.getTransformedAndEncryptedPassword(newData.getPassword()));
        return masterPasswordModel;
    }


    protected void validate(EmailAuthenticateModel passwordAuthenticateModel, NonceData nonceData) {
        //  PasswordEmailValidateData passwordEmailValidateData = new PasswordEmailValidateData();
        //   passwordEmailValidateData.setAuthorizationID(passwordAuthenticateModel.getId());
        //    passwordEmailValidateData.setUsername(nonceData.getUsername());
        //     emailRegisterService.authorizeValidate(passwordEmailValidateData);
    }
}
