package com.froso.ufp.modules.optional.push.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.service.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

class AppdeviceController {
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(AppdeviceController.class);
    @Autowired
    private AppDeviceService simpleAppDeviceService;

    /**
     * Gets app devices.
     *
     * @param request the request
     * @return the app devices
     */
    @RequestMapping(value = "/" + AppDevice.TYPE_NAME, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponse> getAppDevices(HttpServletRequest request) {

        LOGGER.debug("All AppDevices Requested!");
        ResponseHandler manager = new ResponseHandler(request);
        manager.addResult(simpleAppDeviceService.findAll());
        return manager.getResponseEntity();
    }

    /**
     * Delete all.
     *
     * @param request the request
     * @return the response entity
     */
    @RequestMapping(value = "/" + AppDevice.TYPE_NAME, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<BackendResponse> deleteAll(HttpServletRequest request) {

        LOGGER.debug("DeleteAll AppDevices Requested!");
        ResponseHandler manager = new ResponseHandler(request);
        simpleAppDeviceService.deleteAll();
        return manager.getResponseEntity();
    }

    /**
     * Delete by token.
     *
     * @param token   the token
     * @param request the request
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/" + AppDevice.TYPE_NAME + "/{token}", method = RequestMethod.DELETE)
    public ResponseEntity<BackendResponse> deleteByToken(@PathVariable("token") String token,
                                                         HttpServletRequest request) {

        LOGGER.debug("DeleteByToken AppDevices Requested!");
        ResponseHandler manager = new ResponseHandler(request);
        simpleAppDeviceService.deleteAppDeviceWithToken(token);
        return manager.getResponseEntity();
    }

    /**
     * Create app device.
     *
     * @param deviceType   the device type
     * @param token        the token
     * @param customerId   the customer id
     * @param isProduction the is production
     * @param request      the request
     * @return the response entity
     */
    @ResponseBody
    @RequestMapping(value = "/" + AppDevice.TYPE_NAME + "", method = RequestMethod.POST)
    public ResponseEntity<BackendResponse> createAppDevice(@RequestParam("deviceType") String deviceType,
                                                           @RequestParam("token") String token,
                                                           @RequestParam(
                                                                   "customerId") String customerId,
                                                           @RequestParam(value = "isProduction",
                                                                   required = false,
                                                                   defaultValue = "false") Boolean isProduction,
                                                           HttpServletRequest request) {

        AppDevice appDevice = new AppDevice();
        appDevice.setToken(token);
        appDevice.getCoreUser().setId(customerId);
        appDevice.setDeviceType("android".equalsIgnoreCase(deviceType) ? AppDeviceTypeEnum.ANDROID :
                AppDeviceTypeEnum.IOS);
        appDevice.setIsProduction(isProduction);
        LOGGER.debug("Trying to add SimpleAppDevice with userid: " +
                appDevice.getCoreUser().getId() + " and Token " + appDevice.getToken());
        ResponseHandler manager = new ResponseHandler(request);
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
