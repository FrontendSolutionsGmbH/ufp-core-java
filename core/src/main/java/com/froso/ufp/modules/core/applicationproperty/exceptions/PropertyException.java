package com.froso.ufp.modules.core.applicationproperty.exceptions;

import com.froso.ufp.core.exceptions.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: ck
 * Date: 22.01.14
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class PropertyException extends UFPRuntimeException {

    /**
     * Constructor Property exception.
     *
     * @param msg the msg
     */
    public PropertyException(String msg) {

        super(msg);
    }

    /**
     * Constructor Property exception.
     *
     * @param msg   the msg
     * @param cause the cause
     */
    public PropertyException(String msg,
                             Throwable cause) {

        super(msg, cause);
    }
}
