package com.froso.ufp.modules.optional.textbot.expert.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.expert.model.*;
import com.froso.ufp.modules.optional.textbot.expert.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ExpertTextBot.TYPE_NAME)

@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ExpertTextBot.TYPE_NAME)
class AdminExpertTextBotController extends BaseRepositoryController<ExpertTextBot> {


}
