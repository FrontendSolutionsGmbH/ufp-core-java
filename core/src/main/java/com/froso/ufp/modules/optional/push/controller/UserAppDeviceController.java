package com.froso.ufp.modules.optional.push.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import javax.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/" + CoreUser.TYPE_NAME + "/{id}/" + AppDevice.TYPE_NAME)
public class UserAppDeviceController {
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAppDeviceController.class);
    @Autowired
    private AppDeviceService simpleAppDeviceService;

    /**
     * Gets app devices.
     *
     * @param userId  the user id
     * @param request the request
     * @return the app devices
     */
    @ApiOperation(value = "list current user app devices",
            notes = "")

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponse> getAppDevices(@PathVariable("token") String userId,
                                                         HttpServletRequest request) {

        LOGGER.debug("All AppDevices for SimpleUser Requested!");
        ResponseHandler manager = new ResponseHandler(request);
        manager.addResult(simpleAppDeviceService.getAllAppDevicesForUserId(userId));
        return manager.getResponseEntity();
    }

    /**
     * Delete all.
     *
     * @param userId  the user id
     * @param request the request
     * @return the response entity
     */

   /* @ApiOperation(value = "deletes all",
            notes = "")

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<BackendResponse> deleteAll(@PathVariable("token") String userId,
                                                     HttpServletRequest request) {

        LOGGER.debug("DeleteAll AppDevices Requested!");
        ResponseHandler manager = new ResponseHandler(request);
        simpleAppDeviceService.deleteAllFromUser(userId);
        return manager.getResponseEntity();
    }
    */

    /**
     * Delete by token.
     *
     * @param userId  the user id
     * @param token   the token
     * @param request the request
     * @return the response entity
     */

    @ApiOperation(value = "deletes via the application token  ",
            notes = "")

    @RequestMapping(value = "/{appToken}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<BackendResponse> deleteByToken(@PathVariable("token") String userId,
                                                         @PathVariable(
                                                                 "appToken") String token,
                                                         HttpServletRequest request) {

        LOGGER.debug("DeleteByToken AppDevices Requested!");
        ResponseHandler manager = new ResponseHandler(request);
        simpleAppDeviceService.deleteAppDeviceWithToken(token);
        return manager.getResponseEntity();
    }

    /**
     * Create app device for user.
     *
     * @param userId    the user id
     * @param appDevice the simple app device
     * @param request   the request
     * @return the response entity
     */
    @ApiOperation(value = "stores a new application token in backend",
            notes = "")

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BackendResponse> createAppDeviceForUser(@PathVariable(
            "token") String userId,
                                                                  @RequestBody @Valid AppDevice appDevice,
                                                                  HttpServletRequest request) {

        ResponseHandler manager = new ResponseHandler(request);
        // Set Correct CoreUser-Id an appdevice belongs to a user, and hence this is a resource we take it from the resource
        appDevice.getCoreUser().setId(userId);
        LOGGER.debug("Trying to add AppDevice with CustomerId: " +
                appDevice.getCoreUser().getId() + " and Token " + appDevice.getToken());
        if (CreateOrUpdateEnum.CREATED == simpleAppDeviceService.createOrUpdateAppdeviceByToken(appDevice)) {
            manager.addMessage("AppDevice Created");
        } else {
            manager.addMessage("AppDevice Updated");
        }
        // Get the specific Category from db
        manager.addResult(appDevice);
        return manager.getResponseEntity();
    }
}
