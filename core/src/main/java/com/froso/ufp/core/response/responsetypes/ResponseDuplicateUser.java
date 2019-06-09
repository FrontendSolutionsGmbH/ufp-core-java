package com.froso.ufp.core.response.responsetypes;

import com.froso.ufp.core.response.*;

/**
 * Created by ckleinhuix on 04.03.2016.
 */
public class ResponseDuplicateUser {


    /**
     * The Status.
     */
    public String status = ResultStatusEnumCode.ERROR_MONGODB_DUPLICATEKEY.toString();
    /**
     * The Status code.
     */
    public int statusCode = ResultStatusEnumCode.ERROR_MONGODB_DUPLICATEKEY.getCode();
}
