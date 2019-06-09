package com.froso.ufp.modules.core.applicationproperty.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.core.applicationproperty.model.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by alex on 25.02.14.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + ApplicationProperty.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = ApplicationProperty.TYPE_NAME

)
public class AdminApplicationPropertyController extends BaseRepositoryController<ApplicationProperty> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminApplicationPropertyController.class);

    private ApplicationPropertyService applicationPropertyService;

    /**
     * Constructor Admin application property controller.
     *
     * @param applicationPropertyServiceIn the simple application property service in
     */
    @Autowired
    public AdminApplicationPropertyController(ApplicationPropertyService applicationPropertyServiceIn) {

        super();
        applicationPropertyService = applicationPropertyServiceIn;
        logValues();
    }

    private void logValues() {

        Map<String, Properties> map = applicationPropertyService.getProperties();
        LOGGER.info("----------- Printing Properties -----------");
        LOGGER.info("----------- Properties -----------");
        for (Map.Entry<String, Properties> entry : map.entrySet()) {
            LOGGER.info("------ Properties for " + entry.getKey() + " -----------------");
            for (Map.Entry<Object, Object> entry2 : entry.getValue().entrySet()) {
                LOGGER.info(entry2.getKey() + "==" + entry2.getValue());
            }

        }
        LOGGER.info("----------- Printing Properties Finished -----------");

    }

    /**
     * override getter from base class to allow retrieve propterty by name instead of id
     *
     * @param token     the user id
     * @param elementId the element id
     * @param request   the request
     * @return element element
     */

    @Override
    @RequestMapping(value = "/{elementId:.*}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<ApplicationProperty>> readElement
    (@PathVariable String token,
     @PathVariable(
             "elementId") String elementId,
     HttpServletRequest request) {

        try {
            return super.readElement(token, elementId, request);
        } catch (Exception e) {
            // provide means to find element by its key instead of id
            ResponseHandlerTemplateSingleObject<ApplicationProperty> manager = new ResponseHandlerTemplateSingleObject<>(request);
            // Get Element
            ApplicationProperty applicationProperty = applicationPropertyService.findOneBrute(elementId);
            if (applicationProperty == null) {
                applicationProperty = applicationPropertyService.findProperty(elementId);
                if (applicationProperty == null) {
                    applicationProperty = new ApplicationProperty();
                    applicationProperty.setKey(elementId);

//                    throw new ResourceException.ResourceNotAvailable("Resource Not Found ", service.getTypeName(), elementId);
                }

            }
            // Put Transformed JsonMap Output into result
            manager.addResult(applicationProperty);
            return manager.getResponseEntity();
        }
    }

    @ApiOperation(value = "updates an element",
            notes = "Stores the send data in database for a single object." +
                    SwaggerDocSnippets.RESPONSE_START +
                    SwaggerDocSnippets.ERROR_TOKEN_INVALID +
                    SwaggerDocSnippets.ERROR_RESOURCE_NOTAVAILABLE +
                    SwaggerDocSnippets.ERROR_NO_UPDATE_PRIVILEGE +
                    SwaggerDocSnippets.RESPONSE_END)
    @RequestMapping(value = "/{elementId:.*}",
            method = RequestMethod.POST)
    @ResponseBody
    public final ResponseEntity<BackendResponseTemplateSingleObject<ApplicationProperty>> updateElement(
            @PathVariable("token")
                    String userId,
            @PathVariable("elementId")
                    String elementId,
            @RequestBody(required = true)
                    ApplicationProperty dataInParsed,
            HttpServletRequest request) {
        {
            ResponseHandlerTemplateSingleObject<ApplicationProperty> manager = new ResponseHandlerTemplateSingleObject<>(request);
            /** retrieve element by key, if found set incoming objects id to that**/
            ApplicationProperty applicationProperty = applicationPropertyService.findProperty(elementId);
            if (applicationProperty != null) {
                // set id of database element found
                dataInParsed.setId(applicationProperty.getId());
            }
            return super.updateElement(userId, dataInParsed.getId(), dataInParsed, request);

        }

    }

    /**
     * Globals output.
     *
     * @param request the request
     * @return the response entity
     */
    @RequestMapping(value = "/Properties", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BackendResponseTemplateSingleObject<PropertySettingResponse>> globalsOutput
    (@PathVariable String token,
     HttpServletRequest request) {

        ResponseHandlerTemplateSingleObject<PropertySettingResponse> manager = new ResponseHandlerTemplateSingleObject<PropertySettingResponse>(request);
        PropertySettingResponse entries = applicationPropertyService.getProperties();
        manager.addResult(entries);
        return manager.getResponseEntity();
    }
}

