package com.froso.ufp.modules.optional.authenticate.email.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.register.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import org.apache.commons.validator.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class EmailRegisterService extends AbstractRegisterService {
    public static final String TEMPLATE_EMAIL_WELCOME_REGISTER = "register-welcome-body.vm";
    public static final String TEMPLATE_EMAIL_WELCOME_REGISTER_SUBJECT = "register-welcome-subject.vm";
    public static final String TEMPLATE_EMAIL_NEW_NONCE_BODY = "email-new-nonce-body.vm";
    public static final String TEMPLATE_EMAIL_NEW_NONCE_SUBJECT = "email-new-nonce-subject.vm";
    private final CoreUserHelperService coreUserHelperService;
    private final AuthenticateNonceService authenticateNonceService;
    private final TemplateService templateService;
    private final EmailAuthenticateCRUDService emailAuthenticateCRUDService;
    private final IPropertyService propertyService;
    private final EmailSenderService emailSenderService;
    private final AuthenticationsService authenticationsService;

    @Autowired
    public EmailRegisterService(RegistrationsService registrationsService,
                                CoreUserHelperService coreUserHelperService,
                                AuthenticateNonceService authenticateNonceService,
                                TemplateService templateService,
                                EmailAuthenticateCRUDService emailAuthenticateCRUDService,
                                IPropertyService propertyService,
                                EmailSenderService emailSenderService,
                                AuthenticationsService authenticationsService

    ) {

        super(registrationsService);
        this.authenticateNonceService = authenticateNonceService;
        this.templateService = templateService;
        this.emailAuthenticateCRUDService = emailAuthenticateCRUDService;
        this.propertyService = propertyService;
        this.emailSenderService = emailSenderService;
        this.authenticationsService = authenticationsService;
        this.coreUserHelperService = coreUserHelperService;

    }

    private String parseEmailTemplate(EmailAuthenticateModel model, String template) {

        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("auth_email");
        HashMap<String, Object> data = new HashMap<>();
        data.put("auth", model);
        //     data.put("validateEmailLink", propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_WEB) + "/" + EmailAuthenticateConstants.PATH_REGISTER + "/" + model.getId() + "/" + model.getdata().getUsername().getUsername());

        byte[] result = templateService.parseTemplateBytes("Default", template, data, settings);
        return new String(result);

    }

    /**
     * Create new nonce password authenticate model.
     *
     * @param model the model
     * @return the password authenticate model
     */
    EmailAuthenticateModel createNewNonce(EmailAuthenticateModel model) {
        model.getData().setNonceData(authenticateNonceService.createNewNonce(model.getData().getNonceData()));
        return emailAuthenticateCRUDService.save(model);
    }

    private void sendLowLevelEmailAndCreateNewNonceBeforeIfNonceTimedOutOrIsNotPresent(EmailAuthenticateModel incomingModel, String templateSubject, String templateBody) {

        // always create a new nonce
        EmailAuthenticateModel emailAuthenticateModel = createNewNonce(incomingModel);
        String emailSubject = parseEmailTemplate(emailAuthenticateModel, templateSubject);
        String emailBody = parseEmailTemplate(emailAuthenticateModel, templateBody);

        emailSenderService.sendMail(emailAuthenticateModel.getData().getEmail(), emailSubject, emailBody);
        //  LowLevelSMS sendLowLevelSMS = lowLevelSMSService.sendSMS(smsAuthenticateModel.getdata().getPassword(), smsString);
        //   smsAuthenticateModel.getLowLevelSMSDataDocumentLink().setId(sendLowLevelSMS.getId());
        emailAuthenticateCRUDService.save(emailAuthenticateModel);
    }

    private void sendRegistrationEmail(EmailAuthenticateModel model) {
        sendLowLevelEmailAndCreateNewNonceBeforeIfNonceTimedOutOrIsNotPresent(model, TEMPLATE_EMAIL_WELCOME_REGISTER_SUBJECT, TEMPLATE_EMAIL_WELCOME_REGISTER);
    }

    /**
     * Send new nonce email.
     *
     * @param model the model
     */
    void sendNewNonceEmail(EmailAuthenticateModel model) {

        sendLowLevelEmailAndCreateNewNonceBeforeIfNonceTimedOutOrIsNotPresent(model, TEMPLATE_EMAIL_NEW_NONCE_SUBJECT, TEMPLATE_EMAIL_NEW_NONCE_BODY);
    }

    /**
     * Create core user for model.
     *
     * @param model the model
     */
    void createCoreUserForModel(EmailAuthenticateModel model) {

        ICoreUser coreUser = coreUserHelperService.createUser(null);
        model.getCoreUser().setId(coreUser.getId());
        emailAuthenticateCRUDService.save(model);
// publish event for associated core user and sms auth
        //   this.publisher.publishEvent(new EmailAuthEntiteitCreatedEvent(coreUser, model, this));

    }

    /**
     * Create pw authentication direct password authenticate model.
     *
     * @param coreUserId the core user id
     * @param data       the data
     * @return the password authenticate model
     */
    public EmailAuthenticateModel createPWAuthenticationDirect(String coreUserId, EmailAuthenticateData data) {
        validateUnused(data);
        EmailAuthenticateModel result = emailAuthenticateCRUDService.createNewDefault();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(data.getEmail())) {

            result.getData().setEmail(data.getEmail());
            result.getCoreUser().setId(coreUserId);
            emailAuthenticateCRUDService.save(result);

            sendRegistrationEmail(result);

            throw new EmailAuthenticateException.NewUserCreatedWelcomeEmailSend();
        } else {
            throw new EmailAuthenticateException.InvalidEmail();
        }

    }

    private TokenData createPWAuthentication(EmailAuthenticateData data) {
        EmailAuthenticateModel result = emailAuthenticateCRUDService.createNewDefault();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(data.getEmail())) {

            result.getData().setEmail(data.getEmail());
            emailAuthenticateCRUDService.save(result);

            sendRegistrationEmail(result);

            if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {
                createCoreUserForModel(result);
                return authenticationsService.finalizeAuthorization(result);
            }

            throw new EmailAuthenticateException.NewUserCreatedWelcomeEmailSend();
        } else {
            throw new EmailAuthenticateException.InvalidEmail();
        }
    }

    private EmailAuthenticateModel createPWAuthenticationAndReturnData(ICoreUser coreUser, EmailAuthenticateData data) {
        EmailAuthenticateModel result = emailAuthenticateCRUDService.createNewDefault();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailValidator.isValid(data.getEmail())) {

            result.getData().setEmail(data.getEmail());
            result.setCoreUser(new DataDocumentLink<ICoreUser>(coreUser.getId()));
            emailAuthenticateCRUDService.save(result);

            sendRegistrationEmail(result);

            result.setCoreUser(new DataDocumentLink<ICoreUser>(coreUser.getId()));
       /*     if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {
                createCoreUserForModel(result);

            }
         */

            //  throw new EmailAuthenticateException.NewUserCreatedWelcomeEmailSend();
        } else {
            throw new EmailAuthenticateException.InvalidEmail();
        }
        return result;
    }

    /**
     * Login or register sms authenticate data.
     *
     * @param data the data
     * @return the token data
     */
    public TokenData registerPWData(EmailAuthenticateData data) {

        EmailAuthenticateModel foundbyEmail = emailAuthenticateCRUDService.findOneByKeyValue("data.email", data.getEmail());

        if (foundbyEmail == null) {
            return createPWAuthentication(data);
        } else {
            throw new EmailAuthenticateException.UserNameOrEmailAlreadyRegistered();

        }

    }

    /**
     * Register pw data and return model password authenticate model.
     *
     * @param coreUser the core user
     * @param data     the data
     * @return the password authenticate model
     */
    public EmailAuthenticateModel registerPWDataAndReturnModel(ICoreUser coreUser, EmailAuthenticateData data) {

        EmailAuthenticateModel foundbyEmail = emailAuthenticateCRUDService.findOneByKeyValue("data.email", data.getEmail());

        if (foundbyEmail == null) {
            return createPWAuthenticationAndReturnData(coreUser, data);
        } else {
            throw new EmailAuthenticateException.UserNameOrEmailAlreadyRegistered();

        }

    }

    private void validateUnused(EmailAuthenticateData data) {

        EmailAuthenticateModel foundbyEmail = emailAuthenticateCRUDService.findOneByKeyValue("data.email", data.getEmail());

        if (foundbyEmail == null) {
            //OK no entry with that email exists
        } else {
            throw new EmailAuthenticateException.UserNameOrEmailAlreadyRegistered();
        }

    }

    private void finishValidation(EmailAuthenticateModel model) {

        model.setVerified(true);
        model.getData().setNonceData(null);
        emailAuthenticateCRUDService.save(model);
    }

    /**
     * Authorize validate.
     *
     * @param emailEmailValidateData the password email validate data
     */
    public void authorizeValidate(EmailEmailValidateData emailEmailValidateData) {

        // Get the specific Category from db
        // response.setContentType(ContentType.TEXT_HTML.toString());
        EmailAuthenticateModel emailAuthenticateModel = emailAuthenticateCRUDService.findOneBrute(emailEmailValidateData.getAuthorizationID());
        if (emailAuthenticateModel == null) {

            throw new EmailAuthenticateException.AuthorizationNotFound();

        } else {
            // check if nonce matches
            if (emailEmailValidateData.getNonce() != null && emailEmailValidateData.getNonce().equals(emailAuthenticateModel.getData().getNonceData().getNonce())) {

                // finish validation of email
                finishValidation(emailAuthenticateModel);
            } else {

                throw new EmailAuthenticateException.InvalidNonce();
            }
        }
    }

    protected String getAuthenticationName() {
        return EmailAuthenticateConstants.PASSWORD_AUTH_NAME;
    }

}
