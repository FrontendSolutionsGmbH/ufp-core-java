package com.froso.ufp.modules.optional.authenticate.email.model;

import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Request a Nonce (number-used-once) to authenticte against UFP Backend via email")
public class EmailAuthenticateDataAuthenticateEmailOnly {
    /**
     * The constant TYPE_NAME.
     */
    @Indexed(unique = true)
    @ApiModelProperty(example = "email/email", notes = "The email to send a new Nonce to")
    private String email;


    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
