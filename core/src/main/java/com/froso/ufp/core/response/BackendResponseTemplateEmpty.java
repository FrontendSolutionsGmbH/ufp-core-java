package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.messaging.*;
import java.util.*;

public class BackendResponseTemplateEmpty {

    @JsonView(JacksonViews.Minimal.class)
    private ResponseStatus info = new ResponseStatus();


    /**
     * Response Constructor, shall be instanciated by the response manager, hence it is package private
     */
    BackendResponseTemplateEmpty() {

    }


    /**
     * Sets message.
     *
     * @param list the list
     */
    public void setMessages(List<UFPResponseMessage> list) {

        info.getMessages().addAll(list);

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
