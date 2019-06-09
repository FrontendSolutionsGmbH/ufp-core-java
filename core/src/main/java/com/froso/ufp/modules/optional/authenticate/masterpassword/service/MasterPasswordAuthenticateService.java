package com.froso.ufp.modules.optional.authenticate.masterpassword.service;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.controller.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.model.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
public class MasterPasswordAuthenticateService extends AbstractAuthenticateService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MasterPasswordAuthenticateCRUDService masterPasswordAuthenticateCRUDService;


    public String getTransformedAndEncryptedPassword(String newPassword) {

        return passwordEncoder.encode(newPassword);
    }


    public List<Authentication> getAuthenticationsForUser(String coreUserId) {


        return masterPasswordAuthenticateCRUDService.getAllEntriesForCoreUserId(coreUserId);


    }

    public Boolean validateUserPassword(String coreUserId, String pw) {

        MasterPasswordModel pws = masterPasswordAuthenticateCRUDService.findOneByKeyValue("coreUser.id", coreUserId);
        if (pws == null) {
            return false;
        }
        return validatePassword(pws.getData().getPassword(), pw);


    }


    public boolean validatePassword(String existingPassword, String incomingPassword) {

        boolean result = passwordEncoder.matches(incomingPassword, existingPassword);
        if (!result) {
            throw new EmailAuthenticateException.InvalidPwUsernameCombination();
        }
        return result;
    }

    private Boolean verifyNonceToModel(MasterPasswordModel model, MasterPasswordAuthenticateData data) {

        String currentPassword = model.getData().getPassword();
        String incomingPassword = data.getPassword();


        // check if stored nonce is in valid duration
        if (!model.isEnabled()) {
            throw new MasterPasswordAuthenticateException.MasterPasswordDisabled();
        }


        if (currentPassword == null) {
            throw new MasterPasswordAuthenticateException.InvalidPassword();
        }
        if (incomingPassword == null) {
            throw new MasterPasswordAuthenticateException.InvalidPassword();
        }
        if (!currentPassword.equals(incomingPassword)) {
            throw new MasterPasswordAuthenticateException.InvalidPassword();
        }
        return validatePassword(currentPassword, incomingPassword);
    }


    protected String getAuthenticationName() {
        return MasterPasswordConstants.NAME;
    }


}
