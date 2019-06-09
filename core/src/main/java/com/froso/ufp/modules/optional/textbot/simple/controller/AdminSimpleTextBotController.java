package com.froso.ufp.modules.optional.textbot.simple.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.simple.model.*;
import com.froso.ufp.modules.optional.textbot.simple.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + SimpleTextBotModel.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = SimpleTextBotModel.TYPE_NAME)
class AdminSimpleTextBotController extends BaseRepositoryController<SimpleTextBotModel> {


}
