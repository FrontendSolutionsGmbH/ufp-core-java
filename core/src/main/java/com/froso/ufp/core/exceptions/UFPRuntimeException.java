package com.froso.ufp.core.exceptions;

import com.froso.ufp.core.response.*;

public class UFPRuntimeException extends RuntimeException implements IUFPException {

    // holder for code and value
    private IResultStatusEnumCode resultStatus = ResultStatusEnumCode.CODE_UNDEFINED;

    public UFPRuntimeException(String message) {

        super(message);
    }

    public UFPRuntimeException(IResultStatusEnumCode resultStatus, String message) {

        super(resultStatus.toString()+' '+message);
        this.resultStatus = resultStatus;
    }

    public UFPRuntimeException(IResultStatusEnumCode resultStatus) {

        super(resultStatus.toString());
        this.resultStatus = resultStatus;
    }

    public UFPRuntimeException(String message, Throwable cause) {

        super(message, cause);
    }

    public UFPRuntimeException(IResultStatusEnumCode resultStatus, Throwable cause) {

        super(resultStatus.toString(),cause);
        this.resultStatus = resultStatus;
    }

    public UFPRuntimeException(IResultStatusEnumCode resultStatus, String message, Throwable cause) {

        super(resultStatus.toString()+' '+message, cause);
        this.resultStatus = resultStatus;
    }

    public UFPRuntimeException() {

        super();
    }

    public UFPRuntimeException(Throwable cause) {

        super(cause);
    }

    public IResultStatusEnumCode getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(IResultStatusEnumCode resultStatus) {
        this.resultStatus = resultStatus;
    }

}
