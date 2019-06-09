package com.froso.ufp.modules.core.session.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.session.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + Session.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = Session.TYPE_NAME)
class AdminSessionController extends BaseRepositoryController<Session> {

}
