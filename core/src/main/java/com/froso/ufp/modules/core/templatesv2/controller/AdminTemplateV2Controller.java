package com.froso.ufp.modules.core.templatesv2.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.optional.media.controller.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * The type Admin template controller.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + FileTemplate.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = FileTemplate.TYPE_NAME)
class AdminTemplateV2Controller extends AdminFileControllerBase<FileTemplate> {


    /**
     * Constructor Admin template controller.
     *
     * @param simpleTemplateService the simple template service
     */
    @Autowired
    public AdminTemplateV2Controller(TemplateService simpleTemplateService) {

        super(simpleTemplateService);
    }


}
