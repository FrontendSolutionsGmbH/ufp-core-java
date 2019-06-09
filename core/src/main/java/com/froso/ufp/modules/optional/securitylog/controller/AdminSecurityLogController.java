package com.froso.ufp.modules.optional.securitylog.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.securitylog.model.*;
import com.froso.ufp.modules.optional.securitylog.service.*;
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
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + SecurityLog.TYPE_NAME)
@Api(description = "Crud Admin Repository", tags = SecurityLog.TYPE_NAME)
class AdminSecurityLogController extends BaseRepositoryController<SecurityLog> {

}
