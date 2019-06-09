package com.froso.ufp.core.response;

import com.froso.ufp.core.domain.interfaces.*;

public class BackendResponseTemplate3SingleObject<T extends IDataObject, Y extends IResponseStatusTyped> extends BackendResponseTemplate3Empty<Y> {

    private T result;

    BackendResponseTemplate3SingleObject(Y infoInput) {
        // create instance
        super(infoInput);
    }


    public T getResult() {

        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
