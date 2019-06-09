package com.froso.ufp.modules.optional.authenticate.ldap.controller.base;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.authenticate.ldap.model.*;
import com.froso.ufp.modules.optional.authenticate.ldap.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = AuthenticateLdap.TYPE_NAME)
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + AuthenticateLdap.TYPE_NAME)
@UFPAdminController
class LdapAuthenticateAdminCRUDController extends BaseRepositoryController<AuthenticateLdap> {


}
