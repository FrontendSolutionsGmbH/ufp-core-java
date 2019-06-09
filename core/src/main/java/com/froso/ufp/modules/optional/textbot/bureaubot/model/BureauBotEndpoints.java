package com.froso.ufp.modules.optional.textbot.bureaubot.model;

import com.froso.ufp.core.domain.documents.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class BureauBotEndpoints extends AbstractDataDocument {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "BureauEndpoints";
    private EndpointType endpointType;
    private String url;
    private String method = "get";

    public BureauBotEndpoints() {

        super(TYPE_NAME);

    }

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

    public EndpointType getEndpointType() {
        return endpointType;
    }

    public void setEndpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
    }
}
