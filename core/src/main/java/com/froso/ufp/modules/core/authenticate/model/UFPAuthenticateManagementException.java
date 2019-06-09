package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;

@UFPExceptionAnnotation(exceptionID = "token-required")
public class UFPAuthenticateManagementException {

    public static class AuthNotValidatedException extends UFPRuntimeException {
        private static final long serialVersionUID = -2416798314145816920L;

        public AuthNotValidatedException() {
            super(AuthenticateResultStatusEnumCode.AUTHORIZATION_NOT_VALIDATED);
        }

        public AuthNotValidatedException(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NONCE_INVALID, cause);
        }
    }


    public static class AuthorizationInvalid extends UFPRuntimeException {
        private static final long serialVersionUID = 8174037591539358407L;

        public AuthorizationInvalid() {
            super(AuthenticateManagerResultStatusEnumCode.AUTHORIZATION_INVALID);
        }

        public AuthorizationInvalid(Throwable cause) {

            super(AuthenticateManagerResultStatusEnumCode.AUTHORIZATION_INVALID, cause);
        }
    }

    public static class AuthorizationNewDataEqualsOldData extends UFPRuntimeException {
        private static final long serialVersionUID = 4886613333339052328L;

        public AuthorizationNewDataEqualsOldData() {
            super(AuthenticateManagerResultStatusEnumCode.NEW_EQUALS_OLD);
        }

        public AuthorizationNewDataEqualsOldData(Throwable cause) {

            super(AuthenticateManagerResultStatusEnumCode.NEW_EQUALS_OLD, cause);
        }
    }


}
