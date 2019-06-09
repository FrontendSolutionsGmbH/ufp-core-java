package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import facebook4j.*;
import facebook4j.auth.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
public class FacebookAuthenticateService extends AbstractAuthenticateService {

    /**
     * The constant PROP_NAME_FB_ID.
     */
    public static final String PROP_NAME_FB_ID = "ufp.optional.authenticate.facebook.app.id";
    /**
     * The constant PROP_NAME_FB_SECRET.
     */
    public static final String PROP_NAME_FB_SECRET = "ufp.optional.authenticate.facebook.app.secret";
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookAuthenticateService.class);

    /**
     * The Property service.
     */
    @Autowired
    private IPropertyService propertyService;
    /**
     * The Entiteit service.
     */
    @Autowired
    private FacebookRegisterService facebookRegisterService;
    /**
     * The Facebook authenticate crud service.
     */
    @Autowired
    private FacebookAuthenticateCRUDServiceAbstract facebookAuthenticateCRUDService;
    @Autowired
    private AuthenticationsService authenticationsService;


    public List<Authentication> getAuthenticationsForUser(String coreUserId) {


        return facebookAuthenticateCRUDService.getAllEntriesForCoreUserId(coreUserId);


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

    private FacebookModel getAuthenticationFromAccessToken(String accessToken) {
        FacebookModel result;
        try {
            Facebook facebook = getFacebookForAccessToken(accessToken);
            LOGGER.info("Facebook is ; " + facebook.users().getMe().toString());
            //  if (authenticatefacebook.users().getMe().isVerified()) {
            // now check if the authenticatefacebook id is already present
            result = facebookAuthenticateCRUDService.findByFacebookId(facebook.users().getMe().getId());
            if (result == null && authenticationsService.isAutoRegisterEnabled()) {
                result = facebookRegisterService.registerViaAccessTokenAndReturnModel(accessToken);
            }
        } catch (Exception ex) {
            throw new FacebookRegisterException.InvalidOAth();

        }
        return result;
    }

    /**
     * Register via access token token data.
     *
     * @param accessToken the access token
     * @return the token data
     * @throws FacebookException the facebook exception
     */
    public TokenData registerViaAccessToken(String accessToken) throws FacebookException {
        FacebookModel facebookModel = getAuthenticationFromAccessToken(accessToken);
        TokenData tokenData = null;
        if (propertyService.getPropertyValueBoolean(AuthenticationsService.PROP_NAME_AUTOLOGIN)) {

            tokenData = authenticationsService.finalizeAuthorization(facebookModel);
        }
        return tokenData;
    }

    /**
     * Gets entry for access token.
     *
     * @param accessToken the access token
     * @return the entry for access token
     * @throws FacebookException the facebook exception
     */
    public FacebookModel getEntryForAccessToken(String accessToken) throws FacebookException {
        return getAuthenticationFromAccessToken(accessToken);
    }

}
