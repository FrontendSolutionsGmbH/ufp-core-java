package com.froso.ufp.modules.optional.authenticate.emailpassword.service;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.controller.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import java.util.*;

public class EmailPasswordAuthenticateService extends AbstractAuthenticateService {

    @Autowired
    private EmailPasswordRegisterService emailPasswordRegisterService;
    @Autowired
    private EmailPasswordAuthenticateCRUDService usernamePasswordAuthenticateCRUDService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encodePassword(String pw) {

        return passwordEncoder.encode(pw);

    }

    public AuthenticateEmailPassword setNewPasswordViaNonce(String nonceValue, EmailPasswordAuthenticateRequestDataPasswordOnly data) {

        AuthenticateEmailPassword model = getNonce(nonceValue);
// set new password in model, remove nonce and savew
        model.getData().setNonceData(null);

        model.getData().setPassword(encodePassword(data.getPassword()));

        return usernamePasswordAuthenticateCRUDService.save(model);

    }

    public AuthenticateEmailPassword getNonce(String nonceValue) {
        AuthenticateEmailPassword model = usernamePasswordAuthenticateCRUDService.findOneByKeyValue("data.nonceData.nonce", nonceValue);
        return model;
    }

    public Boolean validateNonce(String nonceValue) {
        AuthenticateEmailPassword model = getNonce(nonceValue);
        if (model == null) {
            return Boolean.FALSE;
        }
        if (model.getData().getNonceData().getValidUntil().isAfter(DateTime.now())) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public List<Authentication> getAuthenticationsForUser(String coreUserId) {

        return usernamePasswordAuthenticateCRUDService.getAllEntriesForCoreUserId(coreUserId);

    }
                           /*
    private void createCoreUserForUsernamePasswordAuth(AuthenticateEmailPassword UsernamePasswordAuthenticateModel) {
        ICoreUser coreUser = coreUserHelperService.createUser(null);
        UsernamePasswordAuthenticateModel.getCoreUser().setId(coreUser.getId());
        usernamePasswordAuthenticateCRUDService.save(UsernamePasswordAuthenticateModel);
// publish event for associated core user and UsernamePassword auth
        //  this.publisher.publishEvent(new UsernamePasswordEntiteitCreatedEvent(coreUser, UsernamePasswordAuthenticateModel, this));
    }
//                             */
//    private String createNonce() {
//
//        return RandomStringUtils.randomNumeric(propertyService.getPropertyValueInteger(PROP_NAME_UsernamePassword_NONCE_LENGTH));
//
//    }
//
//
//    private Boolean verifyNonceToModel(AuthenticateEmailPassword model, EmailPasswordAuthenticateRequestData data) {
//
//
//        // check if stored nonce is in valid duration
//        throw new EmailPasswordAuthenticateException.UsernamePasswordAuthDisabled();
//
//
//    }

    private TokenData handleFirstTimeNumberRegistered(AuthenticateEmailPassword usernamePasswordAuthenticateModel, EmailPasswordAuthenticateRequestData data) {
        // existant phonenumber, existant coreuser reference

        //  UsernamePasswordAuthenticateModel.getData().setUsername(null);
        usernamePasswordAuthenticateModel.setVerified(true);
        usernamePasswordAuthenticateCRUDService.save(usernamePasswordAuthenticateModel);
        return authenticationsService.finalizeAuthorization(usernamePasswordAuthenticateModel);

    }

    public void createNewNonceForEmail(EmailPasswordAuthenticateRequestResponse data) {
        AuthenticateEmailPassword emailPasswordAuthenticateModel = usernamePasswordAuthenticateCRUDService.findOneByKeyValue("data.email", "=" + data.getEmail());
        if (emailPasswordAuthenticateModel == null) {

            // throw new EmailPasswordAuthenticateException.AuthorizationNotFound();
            // do nothing, accept all incoming

        } else {
            // authentication existant, check if incoming nonce existant,
            // check nonce

            emailPasswordRegisterService.sendPasswordReset(emailPasswordAuthenticateModel, data);

        }
    }

    public TokenData authenticateUsernamePasswordData(EmailPasswordAuthenticateRequestData data) {
        TokenData result = null;
        // check if the phonenumber is already present

        AuthenticateEmailPassword emailPasswordAuthenticateModel = usernamePasswordAuthenticateCRUDService.findOneByKeyValue("data.email", "=" + data.getEmail());

        if (emailPasswordAuthenticateModel != null) {
            if (data.getPassword() == null || !passwordEncoder.matches(data.getPassword(), emailPasswordAuthenticateModel.getData().getPassword())) {

                throw new EmailPasswordAuthenticateException.AuthorizationNotFound();

            } else {
                // authentication existant, check if incoming nonce existant,
                // check nonce

                if (emailPasswordAuthenticateModel.getCoreUser().isLinked()) {
                    // core user link existant
                    result = handleFirstTimeNumberRegistered(emailPasswordAuthenticateModel, data);

                }

            }
        } else {
            throw new EmailPasswordAuthenticateException.AuthorizationNotFound();

        }

        return result;

    }

    protected String getAuthenticationName() {
        return EmailPasswordConstants.NAME;
    }

}
