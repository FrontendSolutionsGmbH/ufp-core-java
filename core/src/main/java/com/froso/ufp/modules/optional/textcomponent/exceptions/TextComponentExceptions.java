package com.froso.ufp.modules.optional.textcomponent.exceptions;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.optional.textcomponent.model.*;

/**
 * Created by ck on 22.06.2016.
 */
@UFPExceptionAnnotation(exceptionID = "token-required")
public class TextComponentExceptions {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class NoLanguage extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public NoLanguage() {
            super(TextComponentStatusEnumCode.NO_LANGUAGE_FOUND);
        }

        /**
         * Instantiates a new Ufp runtime exception.
         *
         * @param cause the cause
         */
        public NoLanguage(Throwable cause) {

            super(TextComponentStatusEnumCode.NO_LANGUAGE_FOUND, cause);
        }
    }


}
