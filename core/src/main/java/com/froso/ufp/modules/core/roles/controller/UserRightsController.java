package com.froso.ufp.modules.core.roles.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.roles.model.*;
import com.froso.ufp.modules.core.roles.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + UFPAuthenticateConstants.SESSION_PATH + "/" + UserRole.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = UserRole.TYPE_NAME)
class UserRightsController {


    @Autowired
    private UserRightService roleRightService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ICoreUserService coreUserService;

    @ApiOperation(value = "get list of allowed user functions"
    )
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<RoleRightsResult>> readElements(
            @PathVariable("token") String userId) {

        ResponseHandlerTemplateSingleObject<RoleRightsResult> manager = new ResponseHandlerTemplateSingleObject<>();
        // obtain current user
        AbstractCoreUser user = (AbstractCoreUser) coreUserService.findOne(userId);
        RoleRightsResult userCapabilities = new RoleRightsResult();
        // iterate of user roles and collect all enabled functions
        for (DataDocumentLink role : user.getRoles()) {
            UserRole roleDef = userRoleService.findOneBrute(role.getId());
            if (roleDef != null) {
                for (DataDocumentLink capability : roleDef.getCapabilities()) {
                    UserRight capDef = roleRightService.findOneBrute(capability.getId());
                    if (capDef != null) {
                        userCapabilities.add(capDef.getName().toLowerCase());
                    }
                }
            }
        }
        manager.addResult(userCapabilities);
        return manager.getResponseEntity();
    }

}
