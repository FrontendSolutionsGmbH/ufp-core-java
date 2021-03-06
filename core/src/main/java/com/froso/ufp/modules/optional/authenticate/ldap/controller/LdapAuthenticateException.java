package com.froso.ufp.modules.optional.authenticate.ldap.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class LdapAuthenticateException {
    /**
     * Instantiates a new UsernamePassword authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type UsernamePassword authenticate exception.
     */
    public static class NonceInvalid extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public NonceInvalid() {
            super( LdapResultStatusEnumCode.UsernamePassword_NONCE_INVALID);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public NonceInvalid(Throwable cause) {

            super( LdapResultStatusEnumCode.UsernamePassword_NONCE_INVALID, cause);
        }
    }

    public static class NonceExpired extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public NonceExpired() {
            super(LdapResultStatusEnumCode.UsernamePassword_NONCE_EXPIRED);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public NonceExpired(Throwable cause) {

            super(LdapResultStatusEnumCode.UsernamePassword_NONCE_EXPIRED, cause);
        }
    }

    public static class UsernamePasswordAuthDisabled extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public UsernamePasswordAuthDisabled() {
            super(LdapResultStatusEnumCode.UsernamePassword_AUTHORIZATION_DISABLED);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public UsernamePasswordAuthDisabled(Throwable cause) {

            super(LdapResultStatusEnumCode.UsernamePassword_AUTHORIZATION_DISABLED, cause);
        }
    }

    public static class NonceRequiredNonceSend extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public NonceRequiredNonceSend() {
            super(LdapResultStatusEnumCode.UsernamePassword_NONCE_REQUIRED_NONCE_SEND);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public NonceRequiredNonceSend(Throwable cause) {

            super(LdapResultStatusEnumCode.UsernamePassword_NONCE_REQUIRED_NONCE_SEND, cause);
        }
    }

    public static class FirstTimeNumberNonceSend extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public FirstTimeNumberNonceSend() {
            super(LdapResultStatusEnumCode.UsernamePassword_NEW_NUMBER_NONCE_SEND);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public FirstTimeNumberNonceSend(Throwable cause) {

            super(LdapResultStatusEnumCode.UsernamePassword_NEW_NUMBER_NONCE_SEND, cause);
        }
    }

    public static class AuthorizationNotFound extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public AuthorizationNotFound() {
            super(LdapResultStatusEnumCode.UsernamePassword_INVALID_ACCESS);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public AuthorizationNotFound(Throwable cause) {

            super(LdapResultStatusEnumCode.UsernamePassword_INVALID_ACCESS, cause);
        }
    }

    public static class AuthorizationAlreadyExistant extends UFPRuntimeException {
        /**
         * Instantiates a new UsernamePassword authenticate exception.
         */
        public AuthorizationAlreadyExistant() {
            super(LdapResultStatusEnumCode.UsernamePassword_NUMBER_ALREADY_REGISTERED);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public AuthorizationAlreadyExistant(Throwable cause) {

            super(LdapResultStatusEnumCode.UsernamePassword_NUMBER_ALREADY_REGISTERED, cause);
        }
    }

}
