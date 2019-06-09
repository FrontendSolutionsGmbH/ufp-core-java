package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

public class AuthenticationsRequestResult implements IDataObject {
    private static final long serialVersionUID = -9215103960076938571L;
    private Map<String, List<AuthenticateShortRef>> authentications;

    public Map<String, List<AuthenticateShortRef>> getAuthentications() {
        return authentications;
    }

    public void setAuthentications(Map<String, List<AuthenticateShortRef>> authentications) {
        this.authentications = authentications;
    }
}
