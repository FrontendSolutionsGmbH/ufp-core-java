package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.user.model.*;

import java.util.*;

public class TokenData implements IDataObject {
    private static final long serialVersionUID = 2L;
    private String token;
    private Set<String> rights;
    private String firstName;
    private String lastName;

    public Set<String> getRights() {
        return rights;
    }

    public void setRights(Set<String> rights) {
        this.rights = rights;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
