package com.froso.ufp.modules.optional.authenticate.email.controller.register;

import java.lang.reflect.*;

/**
 * The type CoreUser controller.
 *
 * @author Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 11:15                  The SimpleUser Controller provides access to SimpleUser Management and Login.                  Remark:                  the "GET" to a userId is not implemented, the only way to retrieve a simpleUser-data object is by sending a         post to the login resource
 */
abstract public class GenericRegController {
    /**
     * Gets class of repository.
     *
     * @return the class of repository
     */
    public Class getClassOfRepository() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class) superclass.getActualTypeArguments()[0];
    }
}
