package com.froso.ufp.modules.core.register.model;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class UFPRegisterException {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class AuthNotValidatedException extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public AuthNotValidatedException() {
            super(RegisterResultStatusEnumCode.AUTHORIZATION_NOT_VALIDATED);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public AuthNotValidatedException(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NONCE_INVALID, cause);
        }
    }


}
