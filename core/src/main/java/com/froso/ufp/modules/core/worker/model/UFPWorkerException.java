package com.froso.ufp.modules.core.worker.model;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.exceptions.*;

@UFPExceptionAnnotation(exceptionID = "token-required")
public class UFPWorkerException {
    /**
     * Instantiates a new Sms authenticate exception.
     *
     * @param message the message
     */


    /**
     * The type Sms authenticate exception.
     */
    public static class UFPWorkerCannotBeCreated extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public UFPWorkerCannotBeCreated() {
            super(UFPWorkerErrorEnumCode.UFP_WORKER_CANT_BE_CREATED);
        }
    }

    public static class UFPWorkerCannotBeDeleted extends UFPRuntimeException {
        /**
         * Instantiates a new Sms authenticate exception.
         */
        public UFPWorkerCannotBeDeleted() {
            super(UFPWorkerErrorEnumCode.UFP_WORKER_CANT_BE_DELETED);
        }
    }
}
