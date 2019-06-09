package com.froso.ufp.modules.optional.authenticate.email.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class EmailAuthenticateException {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class NewUserCreatedWelcomeEmailSend extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public NewUserCreatedWelcomeEmailSend() {
            super(EmailAuthResultStatusEnumCode.NEW_USER_CREATED_VALIDATION_EMAIL_SEND);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public NewUserCreatedWelcomeEmailSend(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.NEW_USER_CREATED_VALIDATION_EMAIL_SEND, cause);
        }
    }

    public static class NewNonceCreatedEmailSend extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public NewNonceCreatedEmailSend() {
            super(EmailAuthResultStatusEnumCode.NEW_NONCE_CREATED_EMAIL_SEND);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public NewNonceCreatedEmailSend(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.NEW_NONCE_CREATED_EMAIL_SEND, cause);
        }
    }

    public static class InvalidPwUsernameCombination extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public InvalidPwUsernameCombination() {
            super(EmailAuthResultStatusEnumCode.INVALID_USERNAME_PASSWORD_COMBINATION);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public InvalidPwUsernameCombination(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.INVALID_USERNAME_PASSWORD_COMBINATION, cause);
        }
    }

    public static class InvalidEmail extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public InvalidEmail() {
            super(EmailAuthResultStatusEnumCode.INVALID_EMAIL);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public InvalidEmail(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.INVALID_EMAIL, cause);
        }
    }

    public static class UserNameOrEmailAlreadyRegistered extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public UserNameOrEmailAlreadyRegistered() {
            super(EmailAuthResultStatusEnumCode.USERNAME_OR_EMAIL_ALREADY_REGISTERED);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public UserNameOrEmailAlreadyRegistered(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.USERNAME_OR_EMAIL_ALREADY_REGISTERED, cause);
        }
    }

    public static class InvalidNonce extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public InvalidNonce() {
            super(EmailAuthResultStatusEnumCode.INVALID_NONCE);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public InvalidNonce(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.INVALID_NONCE, cause);
        }
    }

    public static class AuthorizationNotFound extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public AuthorizationNotFound() {
            super(EmailAuthResultStatusEnumCode.AUTHORIZATION_NOT_FOUND);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public AuthorizationNotFound(Throwable cause) {

            super(EmailAuthResultStatusEnumCode.AUTHORIZATION_NOT_FOUND, cause);
        }
    }


}
