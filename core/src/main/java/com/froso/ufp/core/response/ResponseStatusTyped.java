package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.messaging.*;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseStatusTyped<T extends Enum<T> & IResultStatusEnumCode> implements IResponseStatusTyped<T> {

    private T status;
    private long durationMilliSeconds;

    private List<UFPResponseMessage> messages;

    public ResponseStatusTyped() {
    }

    @Override
    public List<UFPResponseMessage> getMessages() {
        return messages;
    }

    @Override
    public void setMessageIResultStatusEnums(List<UFPResponseMessage> messages) {
        this.messages = messages;
    }

    @Override
    public T getStatus() {

        return status;
    }

    @Override
    public void setStatus(T status) {

        this.status = status;
    }

    @Override
    @JsonView(JacksonViews.Minimal.class)
    public int getStatusCode() {

        return status.getCode();
    }

    @Override
    public long getDurationMilliSeconds() {

        return durationMilliSeconds;
    }

    @Override
    public void setDurationMilliSeconds(long durationNanoSseconds) {

        this.durationMilliSeconds = durationNanoSseconds;
    }

}
