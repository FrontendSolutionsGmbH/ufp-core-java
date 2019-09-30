package com.froso.ufp.modules.optional.authenticate.emailpassword.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.register.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.controller.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class EmailPasswordRegisterService extends AbstractRegisterService {
    public static final String EMAILPASSWORD_RESET_EMAIL_TEMPLATE = "emailpassword-authenticate-resetpw.vm";
    public static final String EMAILPASSWORD_RESET_EMAIL_WELCOME_TEMPLATE_SUBJECT = "emailpassword-authenticate-welcome-resetpw-subject.vm";
    public static final String EMAILPASSWORD_RESET_EMAIL_WELCOME_TEMPLATE = "emailpassword-authenticate-welcome-resetpw.vm";
    public static final String EMAILPASSWORD_RESET_EMAIL_TEMPLATE_SUBJECT = "emailpassword-authenticate-resetpw-subject.vm";

    private TemplateService templateService;

    private AuthenticateNonceService authenticateNonceService;
    private EmailPasswordAuthenticateCRUDService emailPasswordAuthenticateCRUDService;
    private ICoreUserService coreUserService;
    private EmailPasswordAuthenticateCRUDService usernamePasswordAuthenticateCRUDService;
    private IPropertyService propertyService;
    private EmailSenderService emailSenderService;
    private AuthenticationsService authenticationsService;

    @Autowired
    public EmailPasswordRegisterService(
            RegistrationsService registrationsService,
            TemplateService templateService,
            AuthenticateNonceService authenticateNonceService,
            EmailPasswordAuthenticateCRUDService emailPasswordAuthenticateCRUDService,
            ICoreUserService coreUserService,
            EmailPasswordAuthenticateCRUDService usernamePasswordAuthenticateCRUDService,
            IPropertyService propertyService,
            EmailSenderService emailSenderService,
            AuthenticationsService authenticationsService

    ) {

        super(registrationsService);
        this.authenticationsService = authenticationsService;
        this.templateService = templateService;
        this.emailPasswordAuthenticateCRUDService = emailPasswordAuthenticateCRUDService;
        this.coreUserService = coreUserService;
        this.authenticateNonceService = authenticateNonceService;
        this.usernamePasswordAuthenticateCRUDService = usernamePasswordAuthenticateCRUDService;
        this.propertyService = propertyService;
        this.emailSenderService = emailSenderService;

    }

    public void sendPasswordReset(AuthenticateEmailPassword model, EmailPasswordAuthenticateRequestResponse data) {

        if (!model.isVerified() ) {
            // send welcome mail if no password present yet ...
            sendLowLevelEmailAndCreateNewNonceBeforeIfNonceTimedOutOrIsNotPresent(model, data, EMAILPASSWORD_RESET_EMAIL_WELCOME_TEMPLATE_SUBJECT, EMAILPASSWORD_RESET_EMAIL_WELCOME_TEMPLATE);

        } else {
            sendLowLevelEmailAndCreateNewNonceBeforeIfNonceTimedOutOrIsNotPresent(model, data, EMAILPASSWORD_RESET_EMAIL_TEMPLATE_SUBJECT, EMAILPASSWORD_RESET_EMAIL_TEMPLATE);

        }
    }

    private String parseEmailTemplate(Map<String, Object> dataIn, String template) {

        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("auth_emailpassword");
        HashMap<String, Object> data = new HashMap<>();
//        data.put("auth", model);
        data.putAll(dataIn);

        byte[] result = templateService.parseTemplateBytes("Default", template, data, settings);
        return new String(result);

    }

    /**
     * Create new nonce email password authenticate model.
     *
     * @param model the model
     * @return the email password authenticate model
     */
    AuthenticateEmailPassword createNewNonce(AuthenticateEmailPassword model) {
        model.getData().setNonceData(authenticateNonceService.createNewNonce(model.getData().getNonceData()));
        return model;
    }

    private void sendLowLevelEmailAndCreateNewNonceBeforeIfNonceTimedOutOrIsNotPresent(AuthenticateEmailPassword incomingModel, EmailPasswordAuthenticateRequestResponse request, String templateSubject, String templateBody) {

        createNewNonce(incomingModel);
        emailPasswordAuthenticateCRUDService.save(incomingModel);

        Map<String, Object> data = new HashMap<>();
        data.put("user", coreUserService.findOne(incomingModel.getCoreUser().getId()));
        data.put("auth", incomingModel);

        // parse request/response urls

        String returnUrl = request.getRequestResponse().getReturnSuccessUrl();
        // replace the nonce value in return url here
        request.getRequestResponse().setReturnSuccessUrl(
                returnUrl.replace("${nonce}", incomingModel.getData().getNonceData().getNonce())
        );

        data.put("request", request.getRequestResponse());
        // always create a new nonce
        AuthenticateEmailPassword emailAuthenticateModel = createNewNonce(incomingModel);
        String emailSubject = parseEmailTemplate(data, templateSubject);
        String emailBody = parseEmailTemplate(data, templateBody);

        emailSenderService.sendMail(emailAuthenticateModel.getData().getEmail(), emailSubject, emailBody, incomingModel.getCoreUser() != null ? incomingModel.getCoreUser().getId() : null);
        //  LowLevelSMS sendLowLevelSMS = lowLevelSMSService.sendSMS(smsAuthenticateModel.getdata().getPassword(), smsString);
        //   smsAuthenticateModel.getLowLevelSMSDataDocumentLink().setId(sendLowLevelSMS.getId());
        emailPasswordAuthenticateCRUDService.save(emailAuthenticateModel);
    }

    /**
     * Send welcome UsernamePassword.
     *
     * @param model the model
     */
    public void sendWelcomeUsernamePassword(AuthenticateEmailPassword model) {

//        sendLowLevelUsernamePassword(model, SMS_AUTHENTICATE_NONCE_FIRST_UNVERIFIED);

    }

    private AuthenticateEmailPassword handleFirstTimeNumberUnregistered(ICoreUser coreUser, EmailPasswordRegisterData data) {

        AuthenticateEmailPassword model = emailPasswordAuthenticateCRUDService.createNewDefault();
        model.getData().setEmail(data.getEmail());
        model.setCoreUser(new DataDocumentLink<>(coreUser.getId()));
        emailPasswordAuthenticateCRUDService.save(model);
        return model;
    }

    public TokenData register(ICoreUser coreUser, EmailPasswordRegisterData data) {
        // check if the phonenumber is already present

        if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {

            return authenticationsService.finalizeAuthorization(registerAndReturnModel(coreUser, data));
        } else {
            throw new EmailPasswordAuthenticateException.FirstTimeNumberNonceSend();

        }

    }

    public Boolean isEmailAlreadyPresent(String email) {

        return emailPasswordAuthenticateCRUDService.findOneByKeyValue("data.email", email) != null;

    }

    public AuthenticateEmailPassword registerAndReturnModel(ICoreUser coreUser, EmailPasswordRegisterData data) {

        // check if the phonenumber is already present

        AuthenticateEmailPassword usernamePasswordAuthenticateModel = usernamePasswordAuthenticateCRUDService.findOneByKeyValue("data.email", "=" + data.getEmail());
        if (usernamePasswordAuthenticateModel == null) {

            usernamePasswordAuthenticateModel = handleFirstTimeNumberUnregistered(coreUser, data);

        } else {

            throw new EmailPasswordAuthenticateException.AuthorizationAlreadyExistant();

        }

        return usernamePasswordAuthenticateModel;

    }

    protected String getAuthenticationName() {
        return EmailPasswordConstants.NAME;
    }

}
