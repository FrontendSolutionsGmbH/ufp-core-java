package com.froso.ufp.modules.optional.authenticate.email.service;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.email.controller.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class EmailAuthenticateService extends AbstractAuthenticateService {
    /**
     * public static final String PROP_NAME_NONCE_LENGTH = "ufp.controller.authenticate.password.noncelength";
     * private static final Logger LOGGER = LoggerFactory.getLogger(PasswordAuthenticateService.class);
     * The constant PROP_NAME_NONCE_LENGTH.
     */


    @Autowired
    private EmailRegisterService emailRegisterService;
    @Autowired
    private AuthenticateNonceService authenticateNonceService;

    @Autowired
    private EmailAuthenticateCRUDService emailAuthenticateCRUDService;


    @Autowired
    private AuthenticationsService authenticationsService;

    /**
     * Instantiates a new Sms authenticate service.
     */
    public EmailAuthenticateService() {

        super();

    }

    public List<Authentication> getAuthenticationsForUser(String coreUserId) {


        return emailAuthenticateCRUDService.getAllEntriesForCoreUserId(coreUserId);


    }


    private TokenData authenticate(EmailAuthenticateModel model, EmailAuthenticateDataAuthenticate data) {

        if (data.getNonce() == null) {

            // send nonce to user

        }

        if (authenticateNonceService.verifyNonceToModel(model.getCoreUser().getId(), model.getData().getNonceData(), data.getNonce())) {
// delete existing nonce after succesful login
            model.getData().setNonceData(null);
            model.setVerified(true);
            emailAuthenticateCRUDService.save(model);

            return authenticationsService.finalizeAuthorization(model);
        } else {
            throw new EmailAuthenticateException.InvalidNonce();
        }


        //return null;
    }

    /**
     * Login or register sms authenticate data.
     *
     * @param data the data
     * @return the token data
     */
    public TokenData authenticate(EmailAuthenticateDataAuthenticate data) {
        TokenData result = null;

        EmailAuthenticateModel foundbyEmail = emailAuthenticateCRUDService.findOneByKeyValue("data.email", data.getEmail());


        if (foundbyEmail == null) {
            throw new EmailAuthenticateException.AuthorizationNotFound();
        } else {
            // check if the found ones are equal
            if (foundbyEmail != null) {
                result = authenticate(foundbyEmail, data);
            }

        }
        return result;

    }

    /**
     * Request nonce.
     *
     * @param data the data
     */
    public void requestNonce(EmailAuthenticateDataAuthenticateEmailOnly data) {
        requestNonce(data.getEmail());
    }

    /**
     * Request nonce.
     *
     * @param email the email
     */
    public void requestNonce(String email) {

        EmailAuthenticateModel foundbyEmail = emailAuthenticateCRUDService.findOneByKeyValue("data.email", "=" + email);


        if (foundbyEmail == null) {
            throw new EmailAuthenticateException.AuthorizationNotFound();
        } else {
            emailRegisterService.sendNewNonceEmail(foundbyEmail);
            throw new EmailAuthenticateException.NewNonceCreatedEmailSend();
        }


    }

    protected String getAuthenticationName() {
        return EmailAuthenticateConstants.PASSWORD_AUTH_NAME;
    }


}
