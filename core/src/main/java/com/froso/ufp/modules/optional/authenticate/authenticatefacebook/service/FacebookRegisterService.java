package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.register.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.events.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import facebook4j.*;
import facebook4j.auth.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
public class FacebookRegisterService extends AbstractRegisterService {

    public static final String PROP_NAME_FB_ID = "ufp.optional.authenticate.facebook.app.id";
    public static final String PROP_NAME_FB_SECRET = "ufp.optional.authenticate.facebook.app.secret";
    //    private static final String FacebookProfileImageUrl = "https://graph.facebook.com/${facebookId}/picture";
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookRegisterService.class);
    private final IPropertyService propertyService;
    private final ICoreUserService entiteitService;
    private final CoreUserHelperService coreUserHelperService;
    private final FacebookAuthenticateCRUDServiceAbstract facebookAuthenticateCRUDService;
    private final AuthenticationsService authenticationsService;

    @Autowired
    public FacebookRegisterService(RegistrationsService registrationsService, IPropertyService propertyService, ICoreUserService entiteitService, CoreUserHelperService coreUserHelperService, FacebookAuthenticateCRUDServiceAbstract facebookAuthenticateCRUDService, AuthenticationsService authenticationsService) {
        super(registrationsService);
        this.propertyService = propertyService;
        this.entiteitService = entiteitService;
        this.coreUserHelperService = coreUserHelperService;
        this.facebookAuthenticateCRUDService = facebookAuthenticateCRUDService;
        this.authenticationsService = authenticationsService;
    }

    /**
     * Gets facebook for access token.
     *
     * @param accessToken the access token
     * @return the facebook for access token
     */
    public Facebook getFacebookForAccessToken(String accessToken) {
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(propertyService.getPropertyValue(PROP_NAME_FB_ID), propertyService.getPropertyValue(PROP_NAME_FB_SECRET));
        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
        return facebook;
    }

    protected String getAuthenticationName() {
        return FacebookAuthenticationConstants.NAME;
    }

    private FacebookModel registerUserIfNotExistant(String accessToken) {
        FacebookModel result;
        try {
            Facebook facebook = getFacebookForAccessToken(accessToken);
            LOGGER.info("Facebook is ; " + facebook.users().getMe().toString());
            //  if (authenticatefacebook.users().getMe().isVerified()) {
            // now check if the authenticatefacebook id is already present
            result = facebookAuthenticateCRUDService.findByFacebookId(facebook.users().getMe().getId());
            if (result != null) {

                throw new FacebookRegisterException.FacebookIdAlreadyExisting();
            } else {

                ICoreUser coreUser = coreUserHelperService.createUser(null);
                coreUser.setActive(Boolean.TRUE);
                //coreUser.setRole(UserRoleEnum.ROLE_ADMIN);
                coreUser = (ICoreUser) entiteitService.save(coreUser);
                result = facebookAuthenticateCRUDService.createNewDefault();
                result.getCoreUser().setId(coreUser.getId());
                result.getData().setProfilePictureName(facebook.users().getPictureURL().getFile());
                result.getData().setFacebookId(facebook.users().getMe().getId());
                facebookAuthenticateCRUDService.save(result);

                this.publisher.publishEvent(new FacebookEntiteitCreatedEvent(coreUser, facebook.users().getMe(), this));
            }
            //      }
        } catch (FacebookException ex) {
            throw new FacebookRegisterException.InvalidOAth();

        }
        return result;
    }

    /**
     * Register via access token and return token data token data.
     *
     * @param accessToken the access token
     * @return the token data
     * @throws FacebookException the facebook exception
     */
    public TokenData registerViaAccessTokenAndReturnTokenData(String accessToken) throws FacebookException {
        FacebookModel facebookModel = registerUserIfNotExistant(accessToken);
        TokenData tokenData = null;
        if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {

            tokenData = authenticationsService.finalizeAuthorization(facebookModel);
        }
        return tokenData;
    }

    /**
     * Register via access token and return model facebook model.
     *
     * @param accessToken the access token
     * @return the facebook model
     * @throws FacebookException the facebook exception
     */
    public FacebookModel registerViaAccessTokenAndReturnModel(String accessToken) throws FacebookException {
        FacebookModel facebookModel = registerUserIfNotExistant(accessToken);
        return facebookModel;
    }

    /**
     * Create with existing core user facebook model.
     *
     * @param coreUserId  the core user id
     * @param accessToken the access token
     * @return the facebook model
     */
    public FacebookModel createWithExistingCoreUser(String coreUserId, String accessToken) {

        FacebookModel result;
        try {
            Facebook facebook = getFacebookForAccessToken(accessToken);
            LOGGER.info("Facebnook is ; " + facebook.users().getMe().toString());
            //  if (authenticatefacebook.users().getMe().isVerified()) {
            // now check if the authenticatefacebook id is already present

            result = new FacebookModel();
            result.getCoreUser().setId(coreUserId);
            result.getData().setProfilePictureName(facebook.users().getPictureURL().getFile());
            result.getData().setFacebookId(facebook.users().getMe().getId());
            facebookAuthenticateCRUDService.save(result);

            //      }
        } catch (FacebookException ex) {
            throw new FacebookRegisterException.InvalidOAth();

        }

        return result;
    }

    /**
     * Gets entry for access token.
     *
     * @param accessToken the access token
     * @return the entry for access token
     * @throws FacebookException the facebook exception
     */
    public FacebookModel getEntryForAccessToken(String accessToken) throws FacebookException {
        return registerUserIfNotExistant(accessToken);
    }
}
