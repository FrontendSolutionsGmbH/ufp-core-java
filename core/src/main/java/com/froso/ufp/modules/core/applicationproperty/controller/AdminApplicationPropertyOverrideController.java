package com.froso.ufp.modules.core.applicationproperty.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alex on 25.02.14.
 */
// not yet tested @Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ApplicationPropertyOverride.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ApplicationPropertyOverride.TYPE_NAME


)
public class AdminApplicationPropertyOverrideController extends BaseRepositoryController<ApplicationPropertyOverride> {


}

