package com.froso.ufp.modules.optional.sms.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.sms.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alex on 25.02.14.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + QueueSms.TYPE_NAME)
@Api(description = "Crud Admin Repository", tags = QueueSms.TYPE_NAME)
class AdminLowLevelSMSController extends BaseRepositoryController<QueueSms> {

}
