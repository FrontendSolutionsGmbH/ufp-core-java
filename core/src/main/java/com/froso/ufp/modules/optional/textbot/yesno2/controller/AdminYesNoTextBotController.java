package com.froso.ufp.modules.optional.textbot.yesno2.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.yesno2.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + YesNoTextBotModel.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = YesNoTextBotModel.TYPE_NAME)
class AdminYesNoTextBotController extends BaseRepositoryController<YesNoTextBotModel> {


}
