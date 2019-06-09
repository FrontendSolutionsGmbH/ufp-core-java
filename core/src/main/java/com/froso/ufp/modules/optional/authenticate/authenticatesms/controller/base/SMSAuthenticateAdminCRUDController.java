package com.froso.ufp.modules.optional.authenticate.authenticatesms.controller.base;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.authenticate.authenticatesms.model.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = SMSAuthenticateModel.TYPE_NAME)
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + SMSAuthenticateModel.TYPE_NAME)
@UFPAdminController
class SMSAuthenticateAdminCRUDController extends BaseRepositoryController<SMSAuthenticateModel> {

    @Autowired
    public SMSAuthenticateAdminCRUDController(RepositoryService<SMSAuthenticateModel> service, ObjectMapper mapper) {
        super(service, mapper);
    }

}
