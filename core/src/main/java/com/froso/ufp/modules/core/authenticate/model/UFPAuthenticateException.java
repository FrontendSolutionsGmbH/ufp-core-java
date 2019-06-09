package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;

@UFPExceptionAnnotation(exceptionID = "token-required")
public class UFPAuthenticateException {

    public static class AuthNotValidatedException extends UFPRuntimeException {
        public AuthNotValidatedException() {
            super(AuthenticateResultStatusEnumCode.AUTHORIZATION_NOT_VALIDATED);
        }

        public AuthNotValidatedException(Throwable cause) {

            super(SMSResultStatusEnumCode.SMS_NONCE_INVALID, cause);
        }
    }

}
