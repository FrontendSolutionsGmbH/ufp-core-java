package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.messaging.*;
import java.util.*;

public class BackendResponseTemplate2SingleObject<T extends IDataObject, Y extends Enum<Y> & IResultStatusEnumCode> extends BackendResponseTemplate2Empty<Y> {

    @JsonView(JacksonViews.Minimal.class)
    private T result;


    public BackendResponseTemplate2SingleObject() {

    }


    public void setMessages(List<UFPResponseMessage> list) {

        //   info.getMessages().addAll(list);

    }

    public T getResult() {

        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
