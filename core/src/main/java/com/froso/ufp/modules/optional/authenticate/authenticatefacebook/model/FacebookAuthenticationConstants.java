package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;

/**
 * Created by ck on 05.07.2016.
 */
public class FacebookAuthenticationConstants {

    public static final String NAME = "Facebook";
    public static final String REGISTER_RESOURRCE = NAME;
    public static final String AUTHENTICATE_RESOURRCE = NAME;


    public static final String PATH_REG = UFPRegisterConstants.PATH + "/" + FacebookAuthenticationConstants.REGISTER_RESOURRCE;
    public static final String PATH_AUTH = UFPAuthenticateConstants.PATH + "/" + FacebookAuthenticationConstants.AUTHENTICATE_RESOURRCE;
}
