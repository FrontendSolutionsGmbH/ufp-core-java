package com.froso.ufp.modules.optional.textbot.yesno2.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.yesno2.model.*;
import io.swagger.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + YesNoTextBotConfig.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = YesNoTextBotConfig.TYPE_NAME)
class AdminYesNoTextBotConfigController extends BaseRepositoryController<YesNoTextBotConfig> {


}
