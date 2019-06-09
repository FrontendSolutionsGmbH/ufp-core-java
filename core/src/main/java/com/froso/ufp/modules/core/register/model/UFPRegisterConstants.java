package com.froso.ufp.modules.core.register.model;

import com.froso.ufp.core.*;

/**
 * Created by ck on 05.07.2016.
 */
public class UFPRegisterConstants {
    /**
     * The constant REGISTRATION_DESCRIPTION.
     */
    public static final String REGISTRATION_DESCRIPTION = "Registration";
    /**
     * The constant AUTHENTICATION.
     */
    public static final String TAG = "Account";
    /**
     * The constant PATH.
     */
    public static final String PATH = UFPConstants.API + "/" + TAG;

    /**
     * The constant SESSION_RESOURCE.
     */
    public static final String SESSION_RESOURCE = "Session";
    /**
     * The constant SESSION_PATH.
     */
    public static final String SESSION_PATH = UFPConstants.API + "/" + SESSION_RESOURCE + "/{token}/Account";
}
