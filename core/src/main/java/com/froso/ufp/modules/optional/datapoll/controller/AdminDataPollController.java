package com.froso.ufp.modules.optional.datapoll.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.datapoll.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + PollData.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = PollData.TYPE_NAME)
class AdminDataPollController extends BaseRepositoryController<PollData> {


}
