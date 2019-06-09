package com.froso.ufp.modules.core.templatesv1.controller.admin;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.templatesv1.model.*;
import com.froso.ufp.modules.core.templatesv1.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * The type Admin template controller.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + TemplateV1.TYPE_NAME)
@Api(description = "Crud Admin Repository", tags = TemplateV1.TYPE_NAME)
class AdminTemplateController extends BaseRepositoryController<TemplateV1> {



}
