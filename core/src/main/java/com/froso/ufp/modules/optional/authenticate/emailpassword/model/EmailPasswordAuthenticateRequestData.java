package com.froso.ufp.modules.optional.authenticate.emailpassword.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Authenticate against UFP Backend using username/password combination")
public class EmailPasswordAuthenticateRequestData extends EmailPasswordAuthenticateDataBase implements IDataObject {

    private static final long serialVersionUID = 7329899743781937150L;
}
