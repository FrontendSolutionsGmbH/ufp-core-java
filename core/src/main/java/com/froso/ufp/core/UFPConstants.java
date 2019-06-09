package com.froso.ufp.core;

import com.froso.ufp.core.exceptions.*;

public class UFPConstants {

    public static final String PROPERTY_APPLICATION_HOSTNAME = "application.hostname";
    public static final String PROPERTY_APPLICATION_NAME = "application.name";
    public static final String PROPERTY_APPLICATION_VERSION = "application.version";
    public static final String PROPERTY_APPLICATION_BUILDTIME = "application.buildTime";
    public static final String PROPERTY_APPLICATION_BUILD = "application.build";
    public static final String PROPERTY_APPLICATION_WEB = "application.server.web";
    public static final String UTF8 = "UTF-8";
    public static final String API = "api";
    public static final String UFP_FRONTEND_PROPERTY_APPSPECIFIC = "APPSPECIFIC";
    public static final String ADMIN = "admin";
    public static final String ADMIN_FULL = API + "/" + ADMIN;
    public static final String APPLICATION_DEFAULT_CONTENT_TYPE = "application/json";
    public static final String APPLICATION_DEFAULT_CONTENT_TYPE_UTF8 = APPLICATION_DEFAULT_CONTENT_TYPE + ";charset=" + UTF8;

    private UFPConstants() {
        throw new UtilityClassInstanciationException();
    }
}
