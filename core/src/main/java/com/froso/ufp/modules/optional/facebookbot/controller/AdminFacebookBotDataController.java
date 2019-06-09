package com.froso.ufp.modules.optional.facebookbot.controller;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.facebookbot.model.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ReceivedFacebookBotMessage.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ReceivedFacebookBotMessage.TYPE_NAME)
class AdminFacebookBotDataController extends BaseRepositoryController<ReceivedFacebookBotMessage> {


    @Autowired
    public AdminFacebookBotDataController(RepositoryService<ReceivedFacebookBotMessage> service, ObjectMapper objectMapper) {
        super(service,objectMapper);
    }
}
