package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.response.*;

public class SessionResponseStatusTyped extends ResponseStatusTyped<SessionResultStatusEnumCode> implements IResponseStatusTyped<SessionResultStatusEnumCode> {

    public SessionResponseStatusTyped() {
        super();
        // important, set default status value
        this.setStatus(SessionResultStatusEnumCode.OK);
    }
}
