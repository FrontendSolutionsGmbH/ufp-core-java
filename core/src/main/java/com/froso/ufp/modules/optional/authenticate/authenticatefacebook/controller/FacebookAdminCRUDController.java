package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.controller;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model.*;
import com.froso.ufp.modules.optional.authenticate.authenticatefacebook.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = FacebookModel.TYPE_NAME)
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + FacebookModel.TYPE_NAME)
@UFPAdminController
class FacebookAdminCRUDController extends BaseRepositoryController<FacebookModel> {


}
