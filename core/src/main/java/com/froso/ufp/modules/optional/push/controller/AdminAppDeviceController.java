package com.froso.ufp.modules.optional.push.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + AppDevice.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = AppDevice.TYPE_NAME)
class AdminAppDeviceController extends BaseRepositoryController<AppDevice> {


}
