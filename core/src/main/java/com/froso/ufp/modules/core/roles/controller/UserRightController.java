package com.froso.ufp.modules.core.roles.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.roles.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + UserRight.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = UserRight.TYPE_NAME)
class UserRightController extends BaseRepositoryController<UserRight> {


}
