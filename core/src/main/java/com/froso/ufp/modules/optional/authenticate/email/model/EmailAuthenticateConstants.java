package com.froso.ufp.modules.optional.authenticate.email.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;

/**
 * Created by ck on 05.07.2016.
 */
public class EmailAuthenticateConstants {
    public static final String PASSWORD_AUTH_NAME = "Email";
    public static final String PATH_REGISTER = UFPRegisterConstants.PATH + "/" + PASSWORD_AUTH_NAME;
    public static final String PATH_AUTH = UFPAuthenticateConstants.PATH + "/" + PASSWORD_AUTH_NAME;
}