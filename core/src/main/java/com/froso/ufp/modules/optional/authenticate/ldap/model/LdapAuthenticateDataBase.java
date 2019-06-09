package com.froso.ufp.modules.optional.authenticate.ldap.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class LdapAuthenticateDataBase implements IDataObject {
    /**
     * The constant TYPE_NAME.
     */

    @Indexed(unique = true)
    @ApiModelProperty(example = "example.username", position = 0, notes = "The username", allowEmptyValue = false)
    @NotNull
    private String username;
    @ApiModelProperty(example = "****", position = 1, notes = "A password", dataType = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

}
