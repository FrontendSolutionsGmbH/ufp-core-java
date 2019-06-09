package com.froso.ufp.modules.optional.authenticate.authenticatesms.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import com.froso.ufp.modules.optional.sms.model.*;
import com.froso.ufp.modules.optional.sms.service.*;
import org.apache.commons.lang3.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class SMSAuthenticateService extends AbstractAuthenticateService {

    public static final String PROP_NAME_SMS_NONCE_LENGTH = "ufp.optional.authenticate.sms.noncelength";
    public static final String SMS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED = "sms-authenticate-nonce-first-unverified.vm";
    public static final String SMS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED_FOLLOWUP = "sms-authenticate-nonce-first-unverified-followup.vm";
    public static final String SNS_AUTHENTICATE_NONCE_FOLLOWUP_UNVERIFIED = "sms-authenticate-nonce-followup-unverified.vm";
    public static final String SMS_AUTHENTICATE_NONCE_FOLLOWUP_VERIFIED = "sms-authenticate-nonce-followup-verified.vm";
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSAuthenticateService.class);

    protected final AuthenticateNonceService authenticateNonceService;

    protected final LowLevelSMSService lowLevelSMSService;

    protected final SMSAuthenticateCRUDService smsAuthenticateCRUDService;

    @Autowired
    public SMSAuthenticateService(AuthenticateNonceService authenticateNonceService,
                                  LowLevelSMSService lowLevelSMSService,
                                  SMSAuthenticateCRUDService smsAuthenticateCRUDService) {
        this.authenticateNonceService = authenticateNonceService;
        this.lowLevelSMSService = lowLevelSMSService;
        this.smsAuthenticateCRUDService = smsAuthenticateCRUDService;
    }

    public List<Authentication> getAuthenticationsForUser(String coreUserId) {

        return smsAuthenticateCRUDService.getAllEntriesForCoreUserId(coreUserId);

    }

    private void createCoreUserForSMSAuth(SMSAuthenticateModel smsAuthenticateModel) {
        ICoreUser coreUser = coreUserHelperService.createUser(null);
        smsAuthenticateModel.getCoreUser().setId(coreUser.getId());
        smsAuthenticateCRUDService.save(smsAuthenticateModel);
// publish event for associated core user and sms auth
        //  this.publisher.publishEvent(new SMSEntiteitCreatedEvent(coreUser, smsAuthenticateModel, this));
    }

    private String createNonce() {

        return RandomStringUtils.randomNumeric(applicationPropertyService.getPropertyValueInteger(PROP_NAME_SMS_NONCE_LENGTH));

    }

    private void sendLowLevelSMS(SMSAuthenticateModel smsAuthenticateModel, String template) {

//        // first check if associated lowelevel sms exists
//        if (smsAuthenticateModel.getLowLevelSMSDataDocumentLink().getId() != null) {
//            QueueSms queueSms = lowLevelSMSService.findOne(smsAuthenticateModel.getLowLevelSMSDataDocumentLink().getId());
//            queueSms.getInfo().setStatus(MessageStatusEnum.CANCELLED);
//            lowLevelSMSService.save(queueSms);
//        }

        // always create a new nonce
        smsAuthenticateModel.getData().setNonceData(authenticateNonceService.createNewNonce(smsAuthenticateModel.getData().getNonceData()));

        String smsString = smsAuthenticateCRUDService.parseSMSTemplate(smsAuthenticateModel, template);

        QueueSms sendLowLevelSMS = lowLevelSMSService.sendSMS(smsAuthenticateModel.getData().getPhoneNumber(), smsString, DataDocumentLink.getId(smsAuthenticateModel.getCoreUser()));
//        smsAuthenticateModel.getLowLevelSMSDataDocumentLink().setId(sendLowLevelSMS.getId());
        smsAuthenticateCRUDService.save(smsAuthenticateModel);
    }

    private TokenData handleFirstTimeNumberUnregistered(SMSAuthenticateData data) {
        // non existant phone number
        // create just an entry in the nonce sms auth table, create user entity as soon as sms nonce is entered correctly
        SMSAuthenticateModel smsAuthenticateModel = smsAuthenticateCRUDService.createNewDefault();
        smsAuthenticateModel.getData().setPhoneNumber(smsAuthenticateCRUDService.validatePhoneNumberAndReturnCleaned(data.getPhoneNumber()));
        sendLowLevelSMS(smsAuthenticateModel, SMS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED);
        if (applicationPropertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {

            createCoreUserForSMSAuth(smsAuthenticateModel);
            return authenticationsService.finalizeAuthorization(smsAuthenticateModel);
        } else {
            return null;
//            throw new SMSAuthenticateException.FirstTimeNumberNonceSend();

        }
    }

    private Boolean verifyNonceToModel(SMSAuthenticateModel model, SMSAuthenticateRequestData data) {
        if (null == model) {
            return false;
        }
        if (null == model.getData()) {

            throw new SMSAuthenticateException.AuthorizationNotFound();
        }
        if (null == model.getData().getNonceData()) {

            throw new SMSAuthenticateException.NonceRequired();
        }

        if (null == data.getNonce()) {
            throw new SMSAuthenticateException.NonceInvalid();
        }

        // check if stored nonce is in valid duration
        if (!model.isEnabled()) {
            throw new SMSAuthenticateException.SmsAuthDisabled();
        }
        return authenticateNonceService.verifyNonceToModel(model.getCoreUser().getId(), model.getData().getNonceData(), data.getNonce());
    }

    private TokenData handleSecondTimeNumberUnregistered(SMSAuthenticateModel smsAuthenticateModel, SMSAuthenticateRequestData data) {
        // existant phonenumber, but no coreUser reference yet
        // the process is as follow:
        // if data.nonce is set check nonces
        // if data.nonce is empty, resend nonce sms

        TokenData result = null;
        if (data.getNonce() != null) {

            // verify nonce

            if (verifyNonceToModel(smsAuthenticateModel, data)) {
                // all cool
                smsAuthenticateModel.getData().setNonceData(null);
                smsAuthenticateModel.setVerified(true);
                smsAuthenticateCRUDService.save(smsAuthenticateModel);
                createCoreUserForSMSAuth(smsAuthenticateModel);

                result = authenticationsService.finalizeAuthorization(smsAuthenticateModel);
            }
        } else {

            sendLowLevelSMS(smsAuthenticateModel, SNS_AUTHENTICATE_NONCE_FOLLOWUP_UNVERIFIED);

//            throw new SMSAuthenticateException.NonceRequiredNonceSend();
        }
        return result;
    }

    private TokenData handleFirstTimeNumberRegistered(SMSAuthenticateModel smsAuthenticateModel, SMSAuthenticateRequestData data) {
        // existant phonenumber, existant coreuser reference

        if (data.getNonce() != null) {
            if (verifyNonceToModel(smsAuthenticateModel, data)) {
                smsAuthenticateModel.getData().setNonceData(null);
                smsAuthenticateModel.setVerified(true);
                smsAuthenticateCRUDService.save(smsAuthenticateModel);
                return authenticationsService.finalizeAuthorization(smsAuthenticateModel);
            } else {

                throw new SMSAuthenticateException.NonceInvalid();
            }
        } else {

            throw new SMSAuthenticateException.NonceInvalid();

        }
    }

    public void createNewNonceForPhoneNumber(SMSAuthenticateRequestDataPhonenumberOnly data) {

        Boolean isNew = false;
        SMSAuthenticateModel smsAuthenticateModel = smsAuthenticateCRUDService.findOneByKeyValue("data.phoneNumber", "=" + smsAuthenticateCRUDService.validatePhoneNumberAndReturnCleaned(data.getPhoneNumber()));

        smsAuthenticateCRUDService.validatePhoneNumberAndReturnCleaned(data.getPhoneNumber());
        if (smsAuthenticateModel == null) {

            if (authenticationsService.isAutoRegisterEnabled()) {
                // autoregister is enabled, create a new session
                LOGGER.info("autoRegister enabled, creating authentication for {}", data.getPhoneNumber());
                smsAuthenticateModel = smsAuthenticateCRUDService.createNewDefault();
                smsAuthenticateModel.getData().setPhoneNumber(data.getPhoneNumber());
                smsAuthenticateCRUDService.save(smsAuthenticateModel);
                isNew = true;
            } else {
                /**
                 * throw exception, enable it using applicationproperty settings in AuthenticationsService
                 */
                throw new SMSAuthenticateException.AuthorizationNotFound();
            }

        }

        if (smsAuthenticateModel != null) {
            // authentication existant, check if incoming nonce existant,
            // check nonce
            if (smsAuthenticateModel.getCoreUser().isLinked()) {
                sendLowLevelSMS(smsAuthenticateModel, SMS_AUTHENTICATE_NONCE_FOLLOWUP_VERIFIED);
            } else {
                if (isNew) {
                    // differentiate for first sms ever, and already known, but still not used
                    sendLowLevelSMS(smsAuthenticateModel, SMS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED);
                } else {
                    sendLowLevelSMS(smsAuthenticateModel, SMS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED_FOLLOWUP);
                }
            }
        }
    }

    public TokenData authenticateSMSData(SMSAuthenticateRequestData data) {
        TokenData result = null;
        // check if the phonenumber is already present

        SMSAuthenticateModel smsAuthenticateModel = smsAuthenticateCRUDService.findOneByKeyValue("data.phoneNumber", "=" + smsAuthenticateCRUDService.validatePhoneNumberAndReturnCleaned(data.getPhoneNumber()));
        if (smsAuthenticateModel == null) {

            throw new SMSAuthenticateException.AuthorizationNotFound();
        }

        if (smsAuthenticateModel != null) {
            // authentication existant, check if incoming nonce existant,
            // check nonce

            if (smsAuthenticateModel.getCoreUser().isLinked()) {
                // core user link existant
                result = handleFirstTimeNumberRegistered(smsAuthenticateModel, data);

            } else {
                // core user link NOT existant
                result = handleSecondTimeNumberUnregistered(smsAuthenticateModel, data);
            }
        }

        return result;

    }

    protected String getAuthenticationName() {
        return SMSConstants.NAME;
    }

}
