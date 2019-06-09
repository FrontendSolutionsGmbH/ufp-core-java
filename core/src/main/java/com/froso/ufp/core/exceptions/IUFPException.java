package com.froso.ufp.core.exceptions;

import com.froso.ufp.core.response.*;

/**
 * Created by ckleinhuix on 23/06/2016.
 */
public interface IUFPException {
    /**
     * Gets result status.
     *
     * @return the result status
     */
    IResultStatusEnumCode getResultStatus();
}
