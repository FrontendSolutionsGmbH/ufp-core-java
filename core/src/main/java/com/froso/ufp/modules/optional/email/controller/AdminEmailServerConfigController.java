package com.froso.ufp.modules.optional.email.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.email.model.*;
import com.froso.ufp.modules.optional.email.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alex on 20.11.14.
 */
//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + EmailServerConfig.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = EmailServerConfig.TYPE_NAME)
class AdminEmailServerConfigController extends BaseRepositoryController<EmailServerConfig> {


}
