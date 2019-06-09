package com.froso.ufp.modules.core.workflow.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.workflow.model.trigger.*;
import com.froso.ufp.modules.core.workflow.service.*;
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
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + WorkflowTrigger.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = WorkflowTrigger.TYPE_NAME)
class AdminWorkflowTriggerController extends BaseRepositoryController<WorkflowTrigger> {

}
