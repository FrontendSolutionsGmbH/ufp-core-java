package com.froso.ufp.modules.optional.authenticate.email.model;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class EmailEmailValidateData {


    private String authorizationID;
    private String nonce;

    public String getAuthorizationID() {
        return authorizationID;
    }

    public void setAuthorizationID(String authorizationID) {
        this.authorizationID = authorizationID;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
