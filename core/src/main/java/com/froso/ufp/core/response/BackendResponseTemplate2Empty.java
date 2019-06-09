package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import java.io.*;

public class BackendResponseTemplate2Empty<Y extends Enum<Y> & IResultStatusEnumCode> implements Serializable {

    private IResponseStatusTyped<Y> info = new ResponseStatusTyped();

    public IResponseStatusTyped<Y> getInfo() {
        return info;
    }

    public void setInfo(IResponseStatusTyped<Y> info) {
        this.info = info;
    }
}
