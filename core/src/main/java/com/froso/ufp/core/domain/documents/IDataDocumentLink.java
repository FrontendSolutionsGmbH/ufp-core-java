package com.froso.ufp.core.domain.documents;

import io.swagger.annotations.*;

public interface IDataDocumentLink<T> {
    String getId();

    void setId(String id);

    String getResourceName();

    void setResourceName(String id);

    @ApiModelProperty(hidden = true, required = false, notes = "Referenced Object, usually null")
    T getData();

    void setData(T data);

    Boolean isLinked();
}
