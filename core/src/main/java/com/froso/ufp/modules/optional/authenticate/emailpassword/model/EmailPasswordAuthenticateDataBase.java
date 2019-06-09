package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.simple.validation.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

public abstract class EmailPasswordAuthenticateDataBase implements IDataObject {
    private static final long serialVersionUID = -7473369187488385428L;
    /**
     * The constant TYPE_NAME.
     */

    @Indexed(unique = true)
    @ApiModelProperty(example = "your@email", position = 0, notes = "The username", allowEmptyValue = false)
    @NotNull
    @Pattern(regexp = Validations.REGEXP_EMAIL,
            message = ValidationMessages.INVALID_EMAIL)
    private String email;
    @ApiModelProperty(required = true, example = "****", position = 1, notes = "A password", dataType = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets phone number.
     *
     * @param password the phone number
     */
    public void setPassword(String password) {
        this.password = password;
    }

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
