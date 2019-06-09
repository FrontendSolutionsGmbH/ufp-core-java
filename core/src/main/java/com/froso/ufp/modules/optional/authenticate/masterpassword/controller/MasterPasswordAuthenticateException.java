package com.froso.ufp.modules.optional.authenticate.masterpassword.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.masterpassword.model.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class MasterPasswordAuthenticateException {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class InvalidPassword extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public InvalidPassword() {
            super(MasterPasswordAuthResultStatusEnumCode.MASTER_PASSWORD_INVALID);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public InvalidPassword(Throwable cause) {

            super(MasterPasswordAuthResultStatusEnumCode.MASTER_PASSWORD_INVALID, cause);
        }
    }

    public static class MasterPasswordDisabled extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public MasterPasswordDisabled() {
            super(MasterPasswordAuthResultStatusEnumCode.MASTER_PASSWORD_DISABLED);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public MasterPasswordDisabled(Throwable cause) {

            super(MasterPasswordAuthResultStatusEnumCode.MASTER_PASSWORD_DISABLED, cause);
        }
    }

}
