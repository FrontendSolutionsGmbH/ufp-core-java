package com.froso.ufp.modules.optional.authenticate.ldap.model;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;

/**
 * Created by ck on 07.07.2016.
 */
public class LdapConstants {

    /**
     * The constant NAME.
     */
    public static final String NAME = "Ldap";
    /**
     * The constant PATH_AUTH.
     */
    public static final String PATH_AUTH = UFPAuthenticateConstants.PATH + "/" + LdapConstants.NAME;
    /**
     * The constant PATH_REGISTER.
     */
    public static final String PATH_REGISTER = UFPRegisterConstants.PATH + "/" + LdapConstants.NAME;
}
