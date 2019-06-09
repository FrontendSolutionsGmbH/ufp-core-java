package com.froso.ufp.modules.optional.userresourcefilter.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.userresourcefilter.model.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + com.froso.ufp.modules.optional.userresourcefilter.model.UserResourceFilterModel.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = UserResourceFilterModel.TYPE_NAME)
class AdminUserResourceFilterController extends BaseRepositoryController<com.froso.ufp.modules.optional.userresourcefilter.model.UserResourceFilterModel> {




}
