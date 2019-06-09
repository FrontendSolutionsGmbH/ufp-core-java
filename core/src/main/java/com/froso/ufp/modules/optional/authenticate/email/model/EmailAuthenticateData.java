package com.froso.ufp.modules.optional.authenticate.email.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.simple.validation.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.data.mongodb.core.index.*;

public class EmailAuthenticateData implements IDataObject {


    @ApiModelProperty(required = true, notes = "Email to be used for authentication")
    @NotNull
    @TextIndexed
    @Indexed(unique = true)
    @Pattern(regexp = Validations.REGEXP_EMAIL,
            message = ValidationMessages.INVALID_EMAIL)
    private String email;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private NonceData nonceData = new NonceData();

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
     * Gets nonce data.
     *
     * @return the nonce data
     */
    public NonceData getNonceData() {
        return nonceData;
    }

    /**
     * Sets nonce data.
     *
     * @param nonceData the nonce data
     */
    public void setNonceData(NonceData nonceData) {
        this.nonceData = nonceData;
    }

}
