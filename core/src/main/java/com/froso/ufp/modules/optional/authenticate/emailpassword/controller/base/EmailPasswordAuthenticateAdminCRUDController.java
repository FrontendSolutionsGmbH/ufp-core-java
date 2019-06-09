package com.froso.ufp.modules.optional.authenticate.emailpassword.controller.base;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
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
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = AuthenticateEmailPassword.TYPE_NAME)
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + AuthenticateEmailPassword.TYPE_NAME)
@UFPAdminController
class EmailPasswordAuthenticateAdminCRUDController extends BaseRepositoryController<AuthenticateEmailPassword> {


}
