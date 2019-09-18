package com.froso.ufp.modules.optional.authenticate.emailpassword.controller.base;

import com.froso.ufp.core.annotations.*;
import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = AuthenticateEmailPassword.TYPE_NAME)
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + AuthenticateEmailPassword.TYPE_NAME)
@UFPAdminController
class EmailPasswordAuthenticateAdminCRUDController extends BaseRepositoryController<AuthenticateEmailPassword> {

    @Autowired
    private EmailPasswordAuthenticateService authenticateService;
    @Autowired
    private EmailPasswordRegisterService emailPasswordAuthenticaService;

    /**
     * handle password encoding in controller
     *
     * @param object
     */
    @Override
    protected void prepareSave(AuthenticateEmailPassword object) {

        if (null != object) {
            AuthenticateEmailPassword current = service.findOneBrute(object.getId());
            if (null != current) {
                // template method to update of type T
                if (null == object.getData()) {
                    // restore current
                    object.setData(current.getData());
                }
                if (null == object.getData().getPassword()) {
                    // restore current if send data is null
                    object.getData().setPassword(current.getData().getPassword());

                } else {
                    // encode incoming password
                    object.getData().setPassword(authenticateService.encodePassword(object.getData().getPassword()));
                }
            }
        }
    }

    @ApiOperation(value = "send reset pw to email")
    @RequestMapping(value = "/{id}/resetpw",
            method = RequestMethod.POST)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateEmpty> resetPw(
            @RequestParam String token,
            @RequestParam String id,
            @RequestBody EmailPasswordAuthenticateRequestResponse resetResponse,
            HttpServletRequest request) {

        ResponseHandlerTemplateEmpty manager = new ResponseHandlerTemplateEmpty(request);
        AuthenticateEmailPassword authenticateEmailPassword = service.findOne(id);
        emailPasswordAuthenticaService.sendPasswordReset(authenticateEmailPassword, resetResponse);
        return manager.getResponseEntity();

    }
}
