package com.froso.ufp.modules.optional.messaging.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alex on 25.02.14.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + QueueEmail.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = QueueEmail.TYPE_NAME)
class AdminEMailController extends BaseRepositoryController<QueueEmail> {

}
