package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service;

import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import org.springframework.stereotype.*;

@Service
public class FacebookAuthenticateCRUDServiceAbstract extends AbstractCoreAuthenticationsService<FacebookModel> {
    /**
     * The constant PROP_NAME_FB_ID.
     */
    public static final String PROP_NAME_FB_ID = "ufp.optional.authenticate.facebook.app.id";


    /**
     * Constructor Simple product service.
     */
    public FacebookAuthenticateCRUDServiceAbstract() {

        super(FacebookModel.TYPE_NAME);

    }

    /**
     * Find by facebook id facebook model.
     *
     * @param facebookid the facebookid
     * @return the facebook model
     */
    public FacebookModel findByFacebookId(String facebookid) {
        return findOneByKeyValue("data.facebookId", facebookid);
    }

}
