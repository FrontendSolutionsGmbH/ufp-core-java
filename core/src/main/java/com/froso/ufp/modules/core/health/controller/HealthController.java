package com.froso.ufp.modules.core.health.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.modules.core.health.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * basic health endpoint, returning 200 ok if the controller cann be called
 */
@Controller
@RequestMapping(UFPConstants.API)
@Api(description = "System Health Status",
        tags = "Health")
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthServiceImpl.class);
    private static final ResponseEntity<String> RESPONSE_ENTITY_SUCCESS = new ResponseEntity<>(HttpStatus.NO_CONTENT);

    @Autowired
    public HealthController() {
        LOGGER.debug("HealthController created");
    }

    @RequestMapping(
            value = "/Health",
            method = RequestMethod.GET)
    @ApiOperation(
            value = "Health Status",
            notes = "Health status of backend. This endpoint does not validate anything, it is just signaling that the server is responding and can answer this request. "
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getHealth() {
        // just return 204
    }
//
//    @RequestMapping(
//            value = "/Health/detail",
//            method = RequestMethod.GET)
//    @ApiOperation(
//            value = "Health Status",
//            notes = "Health status of backend"
//    )
//
//    @ResponseBody
//    public ResponseEntity<BackendResponseTemplateSingleObject<HealthStatusMap>> getHealthDetail(HttpServletRequest request) {
//
//        ResponseHandlerTemplateSingleObject<HealthStatusMap> responseHandler = new ResponseHandlerTemplateSingleObject<>(request);
//        return responseHandler.getResponseEntity();
//    }
}
