package com.froso.ufp.core.domain;

import com.froso.ufp.core.domain.interfaces.*;

public class GroupByResult implements IDataObject {

    private static final long serialVersionUID = -4970216812475445542L;
    private Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
