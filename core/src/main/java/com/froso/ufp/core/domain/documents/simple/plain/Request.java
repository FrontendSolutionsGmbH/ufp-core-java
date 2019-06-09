package com.froso.ufp.core.domain.documents.simple.plain;

import java.util.*;

public class Request {

    private Map<String, String> headers;
    private Object body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
