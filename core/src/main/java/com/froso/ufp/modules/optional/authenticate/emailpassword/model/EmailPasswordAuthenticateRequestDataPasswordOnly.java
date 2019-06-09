package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Reset login password")

public class EmailPasswordAuthenticateRequestDataPasswordOnly implements IDataObject {
    private static final long serialVersionUID = 4897442830247165130L;
    /**
     * The constant TYPE_NAME.
     */
    @ApiModelProperty(example = "****", notes = "the new password")
    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
