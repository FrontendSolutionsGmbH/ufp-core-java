package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.response.messaging.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:47
 * <p>
 * The a Response is the Object that is returned on ALL API Requests, each request can result in an arbitrary amount of
 * changed objects the results are then stored in the result field
 *
 * @param <T> the type parameter
 */
public class BackendResponseTemplateSingleObject<T extends IDataObject> extends BackendResponseTemplateEmpty {

    @JsonView(JacksonViews.Minimal.class)
    private T result;
    @JsonView(JacksonViews.Minimal.class)
    private ResponseStatus info;

    /**
     * Response Constructor, shall be instanciated by the response manager, hence it is package private
     */
    public BackendResponseTemplateSingleObject() {

        info = new ResponseStatus();
    }

    @Override
    public ResponseStatus getInfo() {
        return info;
    }

    @Override
    public void setInfo(ResponseStatus info) {
        this.info = info;
    }

    public void setMessages(List<UFPResponseMessage> list) {

        info.getMessages().addAll(list);

    }

    public T getResult() {

        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
