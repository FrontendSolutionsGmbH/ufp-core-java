package com.froso.ufp.modules.optional.push.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.push.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + QueuePushMessage.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = QueuePushMessage.TYPE_NAME)
class AdminPushController extends BaseRepositoryController<QueuePushMessage> {

}
