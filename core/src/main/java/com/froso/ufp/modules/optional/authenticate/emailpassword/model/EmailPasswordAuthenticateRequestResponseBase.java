package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Request a password reset using your email")
public class EmailPasswordAuthenticateRequestResponseBase implements IDataObject {

    private static final long serialVersionUID = -4919499867118391191L;
    private ClientRequestResponse requestResponse = new ClientRequestResponse();
    EmailPasswordAuthenticateRequestResponseBase(){
        // default constructor required
    }
    /**
     * Gets request response.
     *
     * @return the request response
     */
    public ClientRequestResponse getRequestResponse() {
        return requestResponse;
    }

    /**
     * Sets request response.
     *
     * @param requestResponse the request response
     */
    public void setRequestResponse(ClientRequestResponse requestResponse) {
        this.requestResponse = requestResponse;
    }

}
