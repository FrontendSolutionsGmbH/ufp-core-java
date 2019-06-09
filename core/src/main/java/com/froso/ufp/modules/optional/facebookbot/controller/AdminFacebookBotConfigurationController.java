package com.froso.ufp.modules.optional.facebookbot.controller;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.facebookbot.model.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + FacebookBotConfiguration.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = FacebookBotConfiguration.TYPE_NAME)
class AdminFacebookBotConfigurationController extends BaseRepositoryController<FacebookBotConfiguration> {

    @Autowired
    public AdminFacebookBotConfigurationController(RepositoryService<FacebookBotConfiguration> service, ObjectMapper objectMapper) {
        super(service,objectMapper);
    }
}
