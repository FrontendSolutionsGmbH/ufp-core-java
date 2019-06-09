package com.froso.ufp.modules.optional.authenticate.masterpassword.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class MasterPasswordAuthenticateData implements IDataObject {
    /**
     * The constant TYPE_NAME.
     */
    @ApiModelProperty(example = "Test1234")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
