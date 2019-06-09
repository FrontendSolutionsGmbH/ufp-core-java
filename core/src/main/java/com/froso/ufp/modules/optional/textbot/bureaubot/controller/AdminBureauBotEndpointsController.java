package com.froso.ufp.modules.optional.textbot.bureaubot.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.model.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + BureauBotEndpoints.TYPE_NAME)
class AdminBureauBotEndpointsController extends BaseRepositoryController<BureauBotEndpoints> {


}
