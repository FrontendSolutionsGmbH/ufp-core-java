package com.froso.ufp.modules.core.events.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.events.model.*;
import com.froso.ufp.modules.core.events.service.*;
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
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + Event.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = Event.TYPE_NAME)
class AdminEventsController extends BaseRepositoryController<Event> {



}
