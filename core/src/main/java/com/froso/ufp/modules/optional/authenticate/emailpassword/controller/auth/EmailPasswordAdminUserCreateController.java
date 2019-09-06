package com.froso.ufp.modules.optional.authenticate.emailpassword.controller.auth;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.core.roles.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Controller
@RequestMapping(value = EmailPasswordConstants.PATH_REGISTER)
@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION,
        tags = UFPRegisterConstants.TAG)
@UFPPublicController
public class EmailPasswordAdminUserCreateController {
    private static String PROPERTY_NAME_ADMIN_EMAIL = "sendmail.adminemail";

    private final ICoreUserService coreUserService;
    private final IPropertyService propertyService;
    private final UserRoleService userRoleService;

    private final EmailPasswordAuthenticateService emailPasswordAuthenticateService;
    private final EmailPasswordAuthenticateCRUDService emailPasswordAuthenticateCRUDService;

    @Autowired
    public EmailPasswordAdminUserCreateController(IPropertyService propertyService, ICoreUserService coreUserService, UserRoleService userRoleService, EmailPasswordAuthenticateService emailPasswordAuthenticateService, EmailPasswordAuthenticateCRUDService emailPasswordAuthenticateCRUDService) {
        this.propertyService = propertyService;
        this.coreUserService = coreUserService;
        this.userRoleService = userRoleService;
        this.emailPasswordAuthenticateService = emailPasswordAuthenticateService;
        this.emailPasswordAuthenticateCRUDService = emailPasswordAuthenticateCRUDService;
    }

    @RequestMapping(value = "/Admin",
            method = RequestMethod.POST)
    @ApiOperation(value = "create default admin user")
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateEmpty> createAdminUser(@RequestBody EmailPasswordAuthenticateRequestResponseBase data, HttpServletRequest request) {

        ResponseHandlerTemplateEmpty responseHandler = new ResponseHandlerTemplateEmpty(request);


        /* check if email already present as emailpw login */

        AuthenticateEmailPassword model = emailPasswordAuthenticateCRUDService.findOneByKeyValue("data.email", propertyService.getPropertyValue(PROPERTY_NAME_ADMIN_EMAIL));
        if (model == null) {


            /* create new user */
            ICoreUser user = createCoreUser();

            model = emailPasswordAuthenticateCRUDService.createNewDefault();
            model.getData().setEmail(propertyService.getPropertyValue(PROPERTY_NAME_ADMIN_EMAIL));
            model.setCoreUser(new DataDocumentLink<>(user.getId()));
            emailPasswordAuthenticateCRUDService.save(model);

        } else {
            // login already present, do resend pw forgot email
            // BUT: if login already present, make sure the user exists
            ICoreUser user = (ICoreUser) coreUserService.findOneBrute(model.getCoreUser().getId());
            if (user == null) {

                user = createCoreUser();
                model.getCoreUser().setId(user.getId());
                emailPasswordAuthenticateCRUDService.save(model);

            } else {
//                user.setRole(UserRoleEnum.ROLE_ADMIN);
                // when user exists, add role, if already existant the set() will take care of not accpeting it again
                user.getRoles().add(userRoleService.getDefaultAdminRoleLink());
            }

            coreUserService.save(user);
        }

        EmailPasswordAuthenticateRequestResponse loginData = new EmailPasswordAuthenticateRequestResponse();
        loginData.setEmail(model.getData().getEmail());
        loginData.setRequestResponse(data.getRequestResponse());
        emailPasswordAuthenticateService.createNewNonceForEmail(loginData);

        return responseHandler.getResponseEntity(HttpStatus.OK);
    }

    private ICoreUser createCoreUser() {
        // create new user
        ICoreUser user = (ICoreUser) coreUserService.createNewDefault();
        user.setFirstName("UFP");
        user.setLastName("ADMIN");
        user.getRoles().add(userRoleService.getDefaultAdminRoleLink());
        user = (ICoreUser) coreUserService.save(user);
        return user;

    }

}
