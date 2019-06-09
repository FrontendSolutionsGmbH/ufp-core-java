package com.froso.ufp.core.exceptions;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.response.*;
import org.springframework.validation.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class ValidationException2 {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class ValidationException extends UFPRuntimeException {
        /**
         * The Binding result.
         */
        public BindingResult bindingResult;

        /**
         * Instantiates a new Sms authenticate exception.
         *
         * @param bindingResult the binding result
         */
        public ValidationException(BindingResult bindingResult) {

            super(ResultStatusEnumCode.ERROR_VALIDATION);
            this.bindingResult = bindingResult;
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param bindingResult the binding result
         * @param cause         the cause
         */
        public ValidationException(BindingResult bindingResult, Throwable cause) {

            super(ResultStatusEnumCode.ERROR_VALIDATION, cause);
            this.bindingResult = bindingResult;
        }

        public BindingResult getBindingResult() {
            return bindingResult;
        }

        public void setBindingResult(BindingResult bindingResult) {
            this.bindingResult = bindingResult;
        }
    }


}
