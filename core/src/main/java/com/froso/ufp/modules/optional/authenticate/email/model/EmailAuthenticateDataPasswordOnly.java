package com.froso.ufp.modules.optional.authenticate.email.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class EmailAuthenticateDataPasswordOnly implements IDataObject {


    @ApiModelProperty(example = "Test1234")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
