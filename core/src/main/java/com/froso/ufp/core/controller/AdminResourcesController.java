package com.froso.ufp.core.controller;

import com.froso.ufp.core.domain.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/Resources")
@Api(description = "Lists available CRUD Resources", tags = "Resources")
public class AdminResourcesController {

    private final ServiceService serviceService;

    @Autowired
    public AdminResourcesController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @ApiOperation(value = "Informational ufp-backend resource and service configuration", notes = "Informational endpoint it returns live configuration of running backend, used for administration purposes.")
    @RequestMapping(
            method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<ServiceResult>> getResources() {

        ResponseHandlerTemplateSingleObject<ServiceResult> responseHandler = new ResponseHandlerTemplateSingleObject<>();
        responseHandler.addResult(serviceService.getServices());
        return responseHandler.getResponseEntity();
    }

}
