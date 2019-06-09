package com.froso.ufp.modules.optional.theme.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.theme.model.*;
import com.froso.ufp.modules.optional.theme.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ThemeModel.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = ThemeModel.TYPE_NAME)
class AdminThemeController extends BaseRepositoryController<ThemeModel> {


}
