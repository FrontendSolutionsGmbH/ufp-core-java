package com.froso.ufp.modules.core.authenticate.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.service.*;
import org.apache.commons.lang.*;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class AuthenticateNonceService {
    public static final String PROP_NAME_PW_NONCE_LENGTH = "ufp.optional.authenticate.nonce.noncelength";
    public static final String PROP_NAME_NONCE_LENGTH = "ufp.optional.authenticate.sms.noncelength";
    public static final String PROP_NAME_NONCE_DEVNONCE = "ufp.optional.authenticate.nonce.devnonce";
    public static final String PROP_NAME_NONCE_USEDEVNONCE = "ufp.optional.authenticate.nonce.usedevnonce";

    @Autowired(required = false)
    private MasterPasswordAuthenticateService masterPasswordAuthenticateService;

    @Autowired
    private IPropertyService propertyService;

    public AuthenticateNonceService() {

        super();

    }

    private String createNonce() {

        return RandomStringUtils.randomNumeric(propertyService.getPropertyValueInteger(PROP_NAME_PW_NONCE_LENGTH));

    }

    public NonceData createNewNonce(NonceData nonceData) {
        NonceData result = nonceData;
        if (result == null) {
            result = new NonceData();
        }
        if (result.getNonce() == null ||
                result.getNonce().isEmpty() ||
                DateTime.now().isAfter(result.getValidUntil())) {
            // create new nonce
            result.setNonce(createNonce());
            result.setValidUntil(DateTime.now().plusDays(1));

        }
        return result;
    }

    public Boolean useDevNonce() {
        return propertyService.getPropertyValueBoolean(PROP_NAME_NONCE_USEDEVNONCE);
    }

    public Boolean getNonceLength() {
        return propertyService.getPropertyValueBoolean(PROP_NAME_NONCE_LENGTH);
    }

    public Boolean verifyNonceToModel(String coreUserId, NonceData model, String nonce) {

        if (null == model) {
            return false;
        }
        if (null == nonce) {
            return false;
        }
//        if (null == coreUserId) {
//            return false;
//        }
        String currentNonce = model.getNonce();
        String incomingNonce = nonce;

        if (propertyService.getPropertyValueBoolean(PROP_NAME_NONCE_USEDEVNONCE)) {
            if (propertyService.getPropertyValue(PROP_NAME_NONCE_DEVNONCE).equals(incomingNonce)) {
                return true;
            }
        }

        if (currentNonce != null) {
            Boolean result = currentNonce.equals(incomingNonce);
            if (!result) {
                // if inequal attempt to verify using the global master password for this account
                if (null != masterPasswordAuthenticateService && masterPasswordAuthenticateService.validateUserPassword(coreUserId, nonce)) {
                    return true;
                }
            }
            if (DateTime.now().isAfter(model.getValidUntil())) {
                throw new SMSAuthenticateException.NonceExpired();
            }

            if (currentNonce == null) {
                throw new SMSAuthenticateException.NonceInvalid();
            }
            if (incomingNonce == null) {
                throw new SMSAuthenticateException.NonceInvalid();
            }

        }

        if ((currentNonce != null) && (currentNonce != null)) {


            if (currentNonce.equals(incomingNonce)) {
            } else {

                throw new SMSAuthenticateException.NonceInvalid();

            }

        } else {
            throw new SMSAuthenticateException.NonceInvalid();
        }

        return true;
    }

}
