package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 31.10.13 Time: 09:47
 * <p>
 * The a Response is the Object that is returned on ALL API Requests, each request can result in an arbitrary amount of
 * changed objects the results are then stored in the result field
 *
 * @param <T> the type parameter
 */
public class BackendResponseTemplate<T extends IDataObject> extends BackendResponseTemplateEmpty {

    @JsonView(JacksonViews.Minimal.class)
    private IDataObjectList<T> result = new DataObjectList<>();


    /**
     * Gets result.
     *
     * @return the result
     */
    public IDataObjectList<T> getResult() {

        return result;
    }

    /**
     * Add result.
     *
     * @param resultData the result data
     */
    public void addResult(T resultData) {

        result.addItem(resultData);
    }

    /**
     * Add result.
     *
     * @param resultData the result data
     */
    public void addResult(IDataObjectList<T> resultData) {

        result = resultData;
    }

    /**
     * Add all.
     *
     * @param resultData the result data
     */
    public void addAll(List<T> resultData) {

        result.addAll(resultData);
    }

}
