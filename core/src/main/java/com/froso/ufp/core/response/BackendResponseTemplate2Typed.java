package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:47
 * <p>
 * The a Response is the Object that is returned on ALL API Requests, each request can result in an arbitrary amount of
 * changed objects the results are then stored in the result field
 *
 * @param <T> the type parameter
 */
public class BackendResponseTemplate2Typed {

    @JsonView(JacksonViews.Minimal.class)


    private ResponseStatus2 info = new ResponseStatus2();

    /**
     * Response Constructor, shall be instanciated by the response manager, hence it is package private
     */
    public BackendResponseTemplate2Typed() {

    }

    public ResponseStatus2 getInfo() {
        return info;
    }

    public void setInfo(ResponseStatus2 info) {
        this.info = info;
    }
}
