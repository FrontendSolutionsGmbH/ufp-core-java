package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Telephone number associated with account")
public class EmailPasswordRegisterData extends EmailPasswordAuthenticateData implements IDataObject {

    private static final long serialVersionUID = 1634834868803292763L;
}
