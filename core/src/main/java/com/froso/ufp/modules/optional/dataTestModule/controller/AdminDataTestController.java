package com.froso.ufp.modules.optional.dataTestModule.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.dataTestModule.model.*;
import com.froso.ufp.modules.optional.dataTestModule.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + DataTestModel.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = DataTestModel.TYPE_NAME)
class AdminDataTestController extends BaseRepositoryController<DataTestModel> {



}
