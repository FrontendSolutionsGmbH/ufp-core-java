package com.froso.ufp.core.domain.documents.simple.plain;

import java.util.*;

/**
 * Created by ckleinhuix on 31.05.2015.
 */
public class RequestLog {

    private String url;
    private String method;

    //private Map<String, String[]> parameters;
    private List<StringMapArray> parameters = new ArrayList<>();

    private Request request;
    private Request response;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<StringMapArray> getParameters() {
        return parameters;
    }

    public void setParameters(List<StringMapArray> parameters) {
        this.parameters = parameters;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getResponse() {
        return response;
    }

    public void setResponse(Request response) {
        this.response = response;
    }
}
