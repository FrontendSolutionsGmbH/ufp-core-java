package com.froso.ufp.modules.optional.textbot.bureaubot.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.model.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + BureauBot.TYPE_NAME)
@Api(description = "FroSo UFP Buerau Controller",
        tags = "FroSo UFP Buerau")
class AdminBureauBotController extends BaseRepositoryController<BureauBot> {

}
