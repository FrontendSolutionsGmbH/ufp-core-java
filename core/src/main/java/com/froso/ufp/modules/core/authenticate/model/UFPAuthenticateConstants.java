package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.*;

public class UFPAuthenticateConstants {

    public static final String AUTHENTICATION_TAG = "Session";
    public static final String AUTHENTICATION = "Session";
    public static final String USER = "User";
    public static final String PATH = UFPConstants.API + "/" + AUTHENTICATION;
    public static final String AUTHENTICATIONS = "Authentications";
    public static final String SESSION_PATH = UFPConstants.API + "/" + AUTHENTICATION + "/{token}";
    public static final String AUTHENTICATIONS_PATH = UFPAuthenticateConstants.SESSION_PATH + "/Account/" + UFPAuthenticateConstants.AUTHENTICATIONS;
    public static final String AUTHENTICATION_RESOURCE_DESCRIPTION = "Authentication";
    public static final String ACCOUNT_MANAGEMENT_DESCRIPTION = "Authentication Management";

}
