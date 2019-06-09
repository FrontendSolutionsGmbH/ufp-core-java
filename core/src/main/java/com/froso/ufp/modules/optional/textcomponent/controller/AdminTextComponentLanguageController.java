package com.froso.ufp.modules.optional.textcomponent.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + TextComponentLanguage.TYPE_NAME)
@Api(description = "Crud Admin Repository", tags = TextComponentLanguage.TYPE_NAME)
public class AdminTextComponentLanguageController extends BaseRepositoryController<TextComponentLanguage> {

}
