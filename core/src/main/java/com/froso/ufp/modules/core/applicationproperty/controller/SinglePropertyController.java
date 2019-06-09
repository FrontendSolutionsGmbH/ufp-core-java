package com.froso.ufp.modules.core.applicationproperty.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import io.swagger.annotations.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by alex on 25.02.14.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ApplicationProperty.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ApplicationProperty.TYPE_NAME


)
public class SinglePropertyController {

    private ApplicationPropertyService applicationPropertyService;


    /**
     * Constructor Admin application property controller.
     *
     * @param applicationPropertyServiceIn the simple application property service in
     */
    @Autowired
    public SinglePropertyController(ApplicationPropertyService applicationPropertyServiceIn) {

        super();
        applicationPropertyService = applicationPropertyServiceIn;
    }


    /**
     * Gets all Elements from the repository
     *
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "set or create single property ",
            notes = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = "/Property", method = RequestMethod.POST)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<ApplicationProperty>> postProperty(
            @PathVariable String token,
            @RequestBody SinglePropertyRequestBody body,
            HttpServletRequest request) {
        ApplicationProperty property = applicationPropertyService.setOrCreateSingleProperty(body.getKey(), body.getValue());
        ResponseHandler manager = new ResponseHandler(request);
        manager.addResult(property);

        return manager.getResponseEntity();

    }


    /**
     * Gets all Elements from the repository
     *
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "get single property ",
            notes = " ",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = "/Property/{key}", method = RequestMethod.GET)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<ApplicationProperty>> getProperty(
            @PathVariable String token,
            @PathVariable String key,
            HttpServletRequest request) {

        ResponseHandler manager = new ResponseHandler(request);
        ApplicationProperty property = applicationPropertyService.getSingleProperty(key, manager);
        manager.addResult(property);

        return manager.getResponseEntity();

    }

    /**
     * Gets all Elements from the repository
     *
     * @return the elements
     * @throws Exception the exception
     */
    @ApiOperation(value = "delete single property ",
            notes = "<p></p> ",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = "/Property/{key:.+}", method = RequestMethod.DELETE)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateEmpty> deleteProperty(
            @PathVariable String token,
            @PathVariable String key,
            HttpServletRequest request) {

        ResponseHandler manager = new ResponseHandler(request);
        applicationPropertyService.deleteSingleProperty(key, manager);
        return manager.getResponseEntity();
    }


}

