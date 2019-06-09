package com.froso.ufp.modules.optional.authenticate.ldap.model;

import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@ApiModel(description = "Authenticate against UFP Backend using username/password combination")
public class LdapAuthenticateRequestData extends LdapAuthenticateDataBase implements IDataObject {

}
