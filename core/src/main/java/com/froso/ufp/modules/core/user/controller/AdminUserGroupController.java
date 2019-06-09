package com.froso.ufp.modules.core.user.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this template use File | Settings | File Templates.
 */
//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + UserGroup.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = UserGroup.TYPE_NAME)

public class AdminUserGroupController extends BaseRepositoryController<UserGroup> {



}
