package com.froso.ufp.modules.core.user.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.user.model.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + CoreUser.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = CoreUser.TYPE_NAME)
class AdminUserController extends BaseRepositoryController<ICoreUser> {


    //  @Autowired
    //  private UnblockAccountCommand unblockAccountCommand;


    /**
     * Unblock user.
     *
     * @param email   the email
     * @param request the request
     * @return response entity
     */
    @RequestMapping(value = "/Unblock", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BackendResponse> unblockUser(@RequestParam(value = "email",
            required = true) String email,
                                                       HttpServletRequest request) {

        ResponseHandler responseHandler = new ResponseHandler(request);
      /*    try {
            SimpleUser simpleUser = userService.getUserByEmailWithoutValidation(email);
            unblockAccountCommand.execute(simpleUser);
        } catch (UserException exception) {
            LOGGER.error("AdminUserController.unblockUser() Exception ", exception);
            responseHandler.setInfo(ResultStatusEnumCode.FATAL_ERROR);
        }*/
        return responseHandler.getResponseEntity();
    }
}
