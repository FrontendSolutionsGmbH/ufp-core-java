package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Authenticate against UFP Backend using a received Nonce")
public class EmailPasswordAuthenticateData extends EmailPasswordAuthenticateDataBase {

    private static final long serialVersionUID = 3513795386091787728L;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NonceData nonceData = new NonceData();

    public NonceData getNonceData() {
        return nonceData;
    }

    public void setNonceData(NonceData nonceData) {
        this.nonceData = nonceData;
    }

}
