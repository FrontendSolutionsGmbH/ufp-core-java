package com.froso.ufp.modules.optional.authenticate.authenticatesms.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;

@UFPExceptionAnnotation(exceptionID = "token-required")
public class SMSAuthenticateException {

    public static class NonceInvalid extends UFPRuntimeException {
        public NonceInvalid() {
            super(SMSResultStatusEnumCode.SMS_NONCE_INVALID);
        }

        public NonceInvalid(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NONCE_INVALID, cause);
        }
    }

    public static class NonceExpired extends UFPRuntimeException {
        public NonceExpired() {
            super(SMSResultStatusEnumCode.SMS_NONCE_EXPIRED);
        }

        public NonceExpired(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NONCE_EXPIRED, cause);
        }
    }

    public static class SmsAuthDisabled extends UFPRuntimeException {
        public SmsAuthDisabled() {
            super(SMSResultStatusEnumCode.SMS_AUTHORIZATION_DISABLED);
        }

        public SmsAuthDisabled(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_AUTHORIZATION_DISABLED, cause);
        }
    }


    public static class NonceRequired extends UFPRuntimeException {

        public NonceRequired() {
            super(SMSResultStatusEnumCode.SMS_NONCE_NOT_INITIALIZED);
        }


        public NonceRequired(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NONCE_NOT_INITIALIZED, cause);
        }
    }

    public static class InvalidPhoneNumber extends UFPRuntimeException {

        public InvalidPhoneNumber() {
            super(SMSResultStatusEnumCode.SMS_INVALID_PHONE_NUMBER);
        }

        public InvalidPhoneNumber(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_INVALID_PHONE_NUMBER, cause);
        }
    }

    public static class AuthorizationNotFound extends UFPRuntimeException {

        public AuthorizationNotFound() {
            super(SMSResultStatusEnumCode.SMS_PHONENUMBER_UNKNOWN);
        }

        public AuthorizationNotFound(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_PHONENUMBER_UNKNOWN, cause);
        }
    }

    public static class AuthorizationAlreadyExistant extends UFPRuntimeException {

        public AuthorizationAlreadyExistant() {
            super(SMSResultStatusEnumCode.SMS_NUMBER_ALREADY_REGISTERED);
        }

        public AuthorizationAlreadyExistant(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NUMBER_ALREADY_REGISTERED, cause);
        }
    }

}
