package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.froso.ufp.core.domain.documents.simple.validation.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Request a password reset using your email")
public class EmailPasswordAuthenticateRequestResponse extends EmailPasswordAuthenticateRequestResponseBase implements IDataObject {
    private static final long serialVersionUID = -6530460886725260300L;
    /**
     * The constant TYPE_NAME.
     */
    @ApiModelProperty(example = "your@email", notes = "the registered email")
    @NotNull
    @Pattern(regexp = Validations.REGEXP_EMAIL,
            message = ValidationMessages.INVALID_EMAIL)
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
