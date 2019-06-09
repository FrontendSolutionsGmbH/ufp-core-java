package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:47
 * <p>
 * The a Response is the Object that is returned on ALL API Requests, each request can result in an arbitrary amount of
 * changed objects the results are then stored in the result field
 */
public final class BackendResponse {

    @JsonView(JacksonViews.Minimal.class)
    private final List<Object> result;
    @JsonView(JacksonViews.Minimal.class)
    private ResponseStatus info;

    /**
     * Response Constructor, shall be instanciated by the response manager, hence it is package private
     */
    BackendResponse() {

        result = new ArrayList<>();
        info = new ResponseStatus();
    }


    /**
     * Gets result.
     *
     * @return the result
     */
    public List<?> getResult() {

        return result;
    }

    /**
     * Add result.
     *
     * @param resultData the result data
     */
    public void addResult(Object resultData) {

        result.add(resultData);
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public ResponseStatus getInfo() {

        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(ResponseStatus info) {

        this.info = info;
    }
}
