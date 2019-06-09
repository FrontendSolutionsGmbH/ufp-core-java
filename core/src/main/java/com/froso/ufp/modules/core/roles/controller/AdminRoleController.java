package com.froso.ufp.modules.core.roles.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.roles.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + RoleDefinition.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = RoleDefinition.TYPE_NAME)
class AdminRoleController extends BaseRepositoryController<RoleDefinition> {


}
