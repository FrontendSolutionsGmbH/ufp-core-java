package com.froso.ufp.modules.core.register.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.register.model.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.session.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.apache.commons.lang3.*;
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
@RequestMapping(UFPRegisterConstants.SESSION_PATH)
@Api(description = UFPAuthenticateConstants.ACCOUNT_MANAGEMENT_DESCRIPTION,
        tags = UFPAuthenticateConstants.USER)
public class RegisterController {


    @Autowired
    private SessionService sessionService;

    /**
     * Gets user.
     *
     * @param userId  the user id
     * @param request the request
     * @return the user
     */
/*
        @ApiOperation(value = "get current session")

        @RequestMapping(value = "{token}", method = RequestMethod.GET)
        @ResponseBody
        public final ResponseEntity<BackendResponseTemplateSingleObject<Session>> getSessionInfo(
                @PathVariable("token") String userId, HttpServletRequest request) {

            ResponseHandlerTemplateSingleObject<Session> manager = new ResponseHandlerTemplateSingleObject<>(request);
            manager.addResult(sessionService.findByToken(userId));
            return manager.getResponseEntity();
        }
    */
    @ApiOperation(value = "delete the account associated with this session id",
            notes = "If the account is active a delete account process is started, all associated data will be deleted after process is finished - the process may include verification through any of the users contact channels")

    @RequestMapping(value = StringUtils.EMPTY,
            method = RequestMethod.DELETE)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<Session>> logoutUser(
            @PathVariable("token")
                    String userId, HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject<Session> manager = new ResponseHandlerTemplateSingleObject<>(request);

        sessionService.deaktivateSessionByToken(userId);
        return manager.getResponseEntity();
    }

}
