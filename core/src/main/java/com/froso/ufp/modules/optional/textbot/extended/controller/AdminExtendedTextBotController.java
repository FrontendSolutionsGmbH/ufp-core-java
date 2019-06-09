package com.froso.ufp.modules.optional.textbot.extended.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.extended.model.*;
import com.froso.ufp.modules.optional.textbot.extended.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ExtendedTextBot.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ExtendedTextBot.TYPE_NAME)
class AdminExtendedTextBotController extends BaseRepositoryController<ExtendedTextBot> {



}
