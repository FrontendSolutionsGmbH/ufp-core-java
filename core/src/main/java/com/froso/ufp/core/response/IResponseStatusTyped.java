package com.froso.ufp.core.response;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.messaging.*;
import java.util.*;

public interface IResponseStatusTyped<T extends Enum<T> & IResultStatusEnumCode> {
    List<UFPResponseMessage> getMessages();

    void setMessageIResultStatusEnums(List<UFPResponseMessage> messages);

    T getStatus();

    void setStatus(T status);

    @JsonView(JacksonViews.Minimal.class)
    int getStatusCode();

    long getDurationMilliSeconds();

    void setDurationMilliSeconds(long durationNanoSseconds);
}
