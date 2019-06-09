package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * Created by ck on 07.07.2016.
 */
public class AuthenticateShortRef<T> implements IDataObject {

    private static final long serialVersionUID = -8845456502156058990L;
    private String id;
    private T data;
    private String type;
    private Boolean enabled;
    private Boolean verified;

    public AuthenticateShortRef() {

    }

    public AuthenticateShortRef(String type, String id, T data) {
        this.type = type;
        this.id = id;
        this.data = data;

    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
