package com.froso.ufp.modules.optional.authenticate.email.controller.base;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import com.froso.ufp.modules.optional.authenticate.email.service.*;
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
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = EmailAuthenticateModel.TYPE_NAME)
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + EmailAuthenticateModel.TYPE_NAME)
@UFPAdminController
class EmailAuthenticateAdminCRUDController extends BaseRepositoryController<EmailAuthenticateModel> {

}
