package com.froso.ufp.core.response;

import java.io.*;

public class BackendResponseTemplate3Empty<Y extends IResponseStatusTyped> implements Serializable {

    private Y info;

    BackendResponseTemplate3Empty(Y infoInput) {
        // create instance
        info = infoInput;
    }

    public Y getInfo() {
        return info;
    }

    public void setInfo(Y info) {
        this.info = info;
    }
}
