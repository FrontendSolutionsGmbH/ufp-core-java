package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class FacebookRegisterException {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class InvalidOAth extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public InvalidOAth() {
            super(FacebookAuthenticateResultStatusEnumCode.FACEBOOK_INVALID_OAUTH_ACCESS_TOKEN);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public InvalidOAth(Throwable cause) {

            super(FacebookAuthenticateResultStatusEnumCode.FACEBOOK_INVALID_OAUTH_ACCESS_TOKEN, cause);
        }
    }

    public static class FacebookIdAlreadyExisting extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public FacebookIdAlreadyExisting() {
            super(FacebookAuthenticateResultStatusEnumCode.FACEBOOK_ID_ALREADY_PRESENT);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public FacebookIdAlreadyExisting(Throwable cause) {

            super(FacebookAuthenticateResultStatusEnumCode.FACEBOOK_ID_ALREADY_PRESENT, cause);
        }
    }

}
