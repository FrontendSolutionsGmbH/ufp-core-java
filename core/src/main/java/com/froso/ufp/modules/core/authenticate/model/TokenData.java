package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.domain.interfaces.*;

public class TokenData implements IDataObject {
    private static final long serialVersionUID = 1L;
    private String token;

    public TokenData() {
    }

    public TokenData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
