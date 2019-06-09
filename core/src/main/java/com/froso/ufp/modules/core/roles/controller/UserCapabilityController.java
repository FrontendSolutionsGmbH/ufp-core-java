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

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/" + UFPAuthenticateConstants.SESSION_PATH + "/" + RoleDefinition.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY,
        tags = RoleDefinition.TYPE_NAME)
class UserCapabilityController {


    @Autowired
    private RoleCapabilityService roleCapabilityService;
    @Autowired
    private RoleDefinitionService roleDefinitionService;
    @Autowired
    private ICoreUserService coreUserService;

    /**
     * Gets all Elements from the repository
     *
     * @param userId  the user id
     * @param request the request
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "get list of allowed user functions"
    )
    @RequestMapping(value = "",
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<RoleCapabilityResult>> readElements(
            @PathVariable("token") String userId) {

        ResponseHandlerTemplateSingleObject<RoleCapabilityResult> manager = new ResponseHandlerTemplateSingleObject<>();
        // obtain current user
        AbstractCoreUser user = (AbstractCoreUser) coreUserService.findOne(userId);
        RoleCapabilityResult userCapabilities = new RoleCapabilityResult();
        // iterate of user roles and collect all enabled functions
        for (DataDocumentLink role : user.getRoles()) {
            RoleDefinition roleDef = roleDefinitionService.findOneBrute(role.getId());
            if (roleDef != null) {
                for (DataDocumentLink capability : roleDef.getCapabilities()) {
                    RoleCapability capDef = roleCapabilityService.findOneBrute(capability.getId());
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
