package com.froso.ufp.modules.optional.authenticate.authenticatesms.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.register.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.sms.model.*;
import com.froso.ufp.modules.optional.sms.service.*;
import com.google.i18n.phonenumbers.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class SMSRegisterService extends AbstractRegisterService {
    public static final String SNS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED = "sms-authenticate-nonce-first-unverified.vm";

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSRegisterService.class);

    private final AuthenticateNonceService authenticateNonceService;
    private final LowLevelSMSService lowLevelSMSService;
    private final SMSAuthenticateCRUDService smsAuthenticateCRUDService;
    private final IPropertyService propertyService;
    private final AuthenticationsService authenticationsService;

    @Autowired
    public SMSRegisterService(RegistrationsService registrationsService, AuthenticateNonceService authenticateNonceService, LowLevelSMSService lowLevelSMSService, SMSAuthenticateCRUDService smsAuthenticateCRUDService, IPropertyService propertyService, AuthenticationsService authenticationsService) {
        super(registrationsService);
        this.authenticateNonceService = authenticateNonceService;
        this.lowLevelSMSService = lowLevelSMSService;
        this.smsAuthenticateCRUDService = smsAuthenticateCRUDService;
        this.propertyService = propertyService;
        this.authenticationsService = authenticationsService;
    }

    private SMSAuthenticateModel sendLowLevelSMS(SMSAuthenticateModel smsAuthenticateModel, String template) {

        // first check if associated lowelevel sms exists
//        if (smsAuthenticateModel.getLowLevelSMSDataDocumentLink().getId() != null) {
//            QueueSms lowLevelSMS = lowLevelSMSService.findOne(smsAuthenticateModel.getLowLevelSMSDataDocumentLink().getId());
//            lowLevelSMS.getInfo().setStatus(MessageStatusEnum.CANCELLED);
//            lowLevelSMSService.save(lowLevelSMS);
//        }

        // always create a new nonce
        smsAuthenticateModel.getData().setNonceData(authenticateNonceService.createNewNonce(smsAuthenticateModel.getData().getNonceData()));
        // smsAuthenticateModel.getdata().setValidUntil(DateTime.now().plusDays(1));
        String smsString = smsAuthenticateCRUDService.parseSMSTemplate(smsAuthenticateModel, template);

        QueueSms sendLowLevelSMS = lowLevelSMSService.sendSMS(smsAuthenticateModel.getData().getPhoneNumber(), smsString);
//        smsAuthenticateModel.getLowLevelSMSDataDocumentLink().setId(sendLowLevelSMS.getId());
        return smsAuthenticateCRUDService.save(smsAuthenticateModel);
    }

    public void sendWelcomeSMS(SMSAuthenticateModel model) {
        sendLowLevelSMS(model, SNS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED);
    }

    private SMSAuthenticateModel handleFirstTimeNumberUnregistered(ICoreUser coreUser, SMSRegisterData data) {
        // non existant phone numberc
        // create just an entry in the nonce sms auth table, create user entity as soon as sms nonce is entered correctly
        SMSAuthenticateModel smsAuthenticateModel = smsAuthenticateCRUDService.createNewDefault();
        smsAuthenticateModel.getData().setPhoneNumber(validatePhoneNumberAndReturnCleaned(data.getPhoneNumber()));
        sendLowLevelSMS(smsAuthenticateModel, SNS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED);
        smsAuthenticateModel.setCoreUser(new DataDocumentLink<ICoreUser>(coreUser.getId()));

        if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {

            return smsAuthenticateModel;
        } else {
            return null;
//            throw new SMSAuthenticateException.FirstTimeNumberNonceSend();

        }

    }

    /*
   private TokenData handleSecondTimeNumberUnregistered(SMSAuthenticateModel smsAuthenticateModel, SMSAuthenticateData data) {
       // existant phonenumber, but no coreUser reference yet
       // the process is as follow:
       // if data.nonce is set check nonces
       // if data.nonce is empty, resend nonce sms

       TokenData result = null;
       if (data.getNonceData() != null) {

           // verify nonce

           if (authenticateNonceService.verifyNonceToModel(smsAuthenticateModel.getCoreUser().getId(), smsAuthenticateModel.getData().getNonceData(), data.getNonceData().getNonce())) {
               // all cool
               smsAuthenticateModel.getData().setNonceData(null);
               smsAuthenticateModel.setVerified(true);
               smsAuthenticateCRUDService.save(smsAuthenticateModel);
               //    cre(smsAuthenticateModel);

               result = authenticationsService.finalizeAuthorization(smsAuthenticateModel);
           }
       } else {

           sendLowLevelSMS(smsAuthenticateModel, SNS_AUTHENTICATE_NONCE_FOLLOWUP_UNVERIFIED);
           throw new SMSAuthenticateException.NonceRequiredNonceSend();
       }
       return result;
   }
   private TokenData handleFirstTimeNumberRegistered(SMSAuthenticateModel smsAuthenticateModel, SMSAuthenticateData data) {
       // existant phonenumber, existant coreuser reference

       if (data.getNonceData() != null) {
           if (authenticateNonceService.verifyNonceToModel(smsAuthenticateModel.getCoreUser().getId(), smsAuthenticateModel.getData().getNonceData(), data.getNonceData().getNonce())) {
               smsAuthenticateModel.getData().setNonceData(null);
               smsAuthenticateModel.setVerified(true);
               smsAuthenticateCRUDService.save(smsAuthenticateModel);
               return authenticationsService.finalizeAuthorization(smsAuthenticateModel);
           } else {

               throw new SMSAuthenticateException.NonceInvalid();
           }
       } else {

           // no nonce present, distinguish between 2 templates, either if nonce in valid date range, or not

           sendLowLevelSMS(smsAuthenticateModel, SNS_AUTHENTICATE_NONCE_FIRST_VERIFIED);

           throw new SMSAuthenticateException.NonceRequiredNonceSend();


       }
   }

                  */
    public String validatePhoneNumberAndReturnCleaned(String phoneNumber) {
        if (phoneNumber == null) {

            throw new SMSAuthenticateException.InvalidPhoneNumber();
        }

        String cleanedUpPhoneNumber = UFPPhoneNumberUtil.phoneNumberRemoveNonNumbers(phoneNumber);

        // validate phonenumber
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber parsedPhone = null;
        try {
            parsedPhone = phoneUtil.parse(cleanedUpPhoneNumber, "DE");
            LOGGER.info("parsed phone number is" + parsedPhone.toString());

            LOGGER.info("parsed phone number is valid?" + phoneUtil.isValidNumber(parsedPhone));
            LOGGER.info("parsed phone number E164" + phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.E164));
            LOGGER.info("parsed phone number INTERNATIONAL" + phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL));
            LOGGER.info("parsed phone number NATIONAL" + phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL));
            if (!phoneUtil.isValidNumber(parsedPhone)) {

                throw new SMSAuthenticateException.InvalidPhoneNumber();
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            throw new SMSAuthenticateException.InvalidPhoneNumber(e);
        }
        return phoneUtil.format(parsedPhone, PhoneNumberUtil.PhoneNumberFormat.E164);

    }

    public TokenData register(ICoreUser coreUser, SMSRegisterData data) {
        // check if the phonenumber is already present

        if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {

            return authenticationsService.finalizeAuthorization(registerAndReturnModel(coreUser, data));
        } else {
            return null;
//            throw new SMSAuthenticateException.FirstTimeNumberNonceSend();

        }

    }

    public SMSAuthenticateModel registerAndReturnModel(ICoreUser coreUser, SMSRegisterData data) {

        // check if the phonenumber is already present

        SMSAuthenticateModel smsAuthenticateModel = smsAuthenticateCRUDService.findOneByKeyValue("data.phoneNumber", "=" + validatePhoneNumberAndReturnCleaned(data.getPhoneNumber()));
        if (smsAuthenticateModel == null) {

            smsAuthenticateModel = handleFirstTimeNumberUnregistered(coreUser, data);

        } else {

            throw new SMSAuthenticateException.AuthorizationAlreadyExistant();

        }

        return smsAuthenticateModel;

    }

    protected String getAuthenticationName() {
        return SMSConstants.NAME;
    }

}
