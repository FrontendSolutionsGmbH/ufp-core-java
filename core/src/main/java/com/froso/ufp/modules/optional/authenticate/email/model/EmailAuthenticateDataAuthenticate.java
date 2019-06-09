package com.froso.ufp.modules.optional.authenticate.email.model;

import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Authenticate against UFP Backend using a received Nonce via email")
public class EmailAuthenticateDataAuthenticate {
    /**
     * The constant TYPE_NAME.
     */
    @Indexed(unique = true)
    @ApiModelProperty(example = "email/email", position = 00, notes = "The email that the Nonce hase been send to")
    @NotNull
    private String email;

    @Indexed(unique = true)
    @NotNull
    @ApiModelProperty(example = "nonce", position = 100, notes = "The received Nonce")
    private String nonce;

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

    /**
     * Gets nonce.
     *
     * @return the nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * Sets nonce.
     *
     * @param nonce the nonce
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
