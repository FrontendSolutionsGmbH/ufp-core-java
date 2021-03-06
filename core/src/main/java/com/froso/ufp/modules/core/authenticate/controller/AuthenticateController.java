package com.froso.ufp.modules.core.authenticate.controller;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.session.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

/**
 * The type Authenticate controller.
 */
@Controller
@RequestMapping(UFPAuthenticateConstants.PATH)
@Api(description = UFPAuthenticateConstants.AUTHENTICATION_RESOURCE_DESCRIPTION, tags = UFPAuthenticateConstants.AUTHENTICATION)
public class AuthenticateController {

    @Autowired
    private SessionService sessionService;

    /**
     * Gets session info.
     *
     * @param userId  the user id
     * @param request the request
     * @return the session info
     */
    @ApiOperation(value = "get current session authenticate controller")

    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate3SingleObject<Session, SessionResponseStatusTyped>> getSessionInfo(
            @PathVariable("token") String userId, HttpServletRequest request) {

        ResponseHandlerTemplate3SingleObject<Session, SessionResponseStatusTyped> manager = new ResponseHandlerTemplate3SingleObject<>(request, new SessionResponseStatusTyped());
        Session session = sessionService.findOneByKeyValue("userLink.id", userId);

        if (session == null) {
            throw new UFPRuntimeException("No User Session found");
        }
        manager.addResult(session);

        return manager.getResponseEntity();
    }

    /**
     * Logout user response entity.
     *
     * @param userId  the user id
     * @param request the request
     * @return the response entity
     */
    @ApiOperation(value = "delete current session",notes = "This operation terminates an active ufp-session ")
    @RequestMapping(value = "{token}", method = RequestMethod.DELETE)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplate3Empty<SessionResponseStatusTyped>> logoutUser(
            @PathVariable("token") String userId, HttpServletRequest request) {

        ResponseHandlerTemplate3Empty<SessionResponseStatusTyped> manager = new ResponseHandlerTemplate3Empty<>(request, new SessionResponseStatusTyped());
        sessionService.setAllSessionsFromUserToInactive(userId);
        return manager.getResponseEntity();
    }

}
