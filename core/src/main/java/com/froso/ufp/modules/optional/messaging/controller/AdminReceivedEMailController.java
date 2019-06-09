package com.froso.ufp.modules.optional.messaging.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.messaging.model.*;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alex on 25.02.14.
 */
//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ReceivedEmail.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ReceivedEmail.TYPE_NAME)
class AdminReceivedEMailController extends BaseRepositoryController<ReceivedEmail> {


}
