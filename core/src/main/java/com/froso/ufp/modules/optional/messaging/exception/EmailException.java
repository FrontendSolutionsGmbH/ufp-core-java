package com.froso.ufp.modules.optional.messaging.exception;

import com.froso.ufp.core.exceptions.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: ck
 * Date: 22.01.14
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class EmailException extends UFPRuntimeException {

    /**
     * Constructor Email exception.
     *
     * @param msg the msg
     */
    public EmailException(String msg) {

        super(msg);
    }

    /**
     * Constructor Email exception.
     *
     * @param msg   the msg
     * @param cause the cause
     */
    public EmailException(String msg,
                          Throwable cause) {

        super(msg, cause);
    }
}
