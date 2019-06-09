package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Authenticates against UFP Backend using Facebook OAuth access token")
public class FacebookAuthenticateData implements IDataObject {
    /**
     * The constant TYPE_NAME.
     */
    @ApiModelProperty(notes = "The Facebook auth token, obtained via client side facebook authentication")
    private String accessToken;

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
