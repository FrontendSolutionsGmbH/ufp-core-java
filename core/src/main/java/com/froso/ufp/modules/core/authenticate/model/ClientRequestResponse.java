package com.froso.ufp.modules.core.authenticate.model;

import io.swagger.annotations.*;

import java.io.*;

@ApiModel(description = "Response Urls for interaction with a client")
public class ClientRequestResponse implements Serializable {

    private static final long serialVersionUID = -9191421414213305822L;
    @ApiModelProperty(notes = "The default return url")
    private String returnUrl;
    @ApiModelProperty(notes = "The success return url, can contain variables defined by server process like ${nonce}")
    private String returnSuccessUrl;
    @ApiModelProperty(notes = "The Fail return url, may contain server defined variables")
    private String returnFailUrl;

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getReturnSuccessUrl() {
        return returnSuccessUrl;
    }

    public void setReturnSuccessUrl(String returnSuccessUrl) {
        this.returnSuccessUrl = returnSuccessUrl;
    }

    public String getReturnFailUrl() {
        return returnFailUrl;
    }

    public void setReturnFailUrl(String returnFailUrl) {
        this.returnFailUrl = returnFailUrl;
    }
}
