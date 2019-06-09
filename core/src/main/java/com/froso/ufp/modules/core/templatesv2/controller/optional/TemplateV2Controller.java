package com.froso.ufp.modules.core.templatesv2.controller.optional;

import com.froso.ufp.core.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import io.swagger.annotations.*;
import java.util.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

/**
 * The type Admin template controller.
 */
@Controller
@RequestMapping(UFPConstants.API + "/" + FileTemplate.TYPE_NAME)
@Api(description = "Template Functionality", tags = FileTemplate.TYPE_NAME)
class TemplateV2Controller {


    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateV2Controller.class);
    /**
     * Constructor Admin template controller.
     *
     * @param simpleTemplateService the simple template service
     */
    @Autowired
    private TemplateService templateService;

    /**
     * Instantiates a new Template v 2 controller.
     *
     * @param requestDefinitionService the request definition service
     */
    @Autowired
    public TemplateV2Controller(RequestDefinitionService requestDefinitionService) {


        requestDefinitionService.registerTokenFreePath("/" + UFPConstants.API + "/" + FileTemplate.TYPE_NAME);

    }

    @Autowired
    public void configWebPath(RequestDefinitionService service) {
        service.registerTokenFreePath("/" + FileTemplate.TYPE_NAME);
    }


    /**
     * Gets page by full path.
     *
     * @param id       the id
     * @param area     the area
     * @param request  the request
     * @param response the response
     * @return the page by full path
     */
    @RequestMapping(value = "/{id}/{area}/**", method = RequestMethod.GET)
    @ApiOperation(value = "Display particular template", notes = "renders a template")

    public ResponseEntity<byte[]> getPageByFullPath(@PathVariable("id") String id, @PathVariable("area") String area, HttpServletRequest request, HttpServletResponse response) {

        //  ResponseHandler.setDefaultHeaders(response);
        String thepath = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        LOGGER.debug("Page Requested with name:" + thepath);
        String requestFile = thepath.substring(thepath.indexOf(area) + 1 + area.length());
        LOGGER.debug("Page restOfTheUrl  with name:" + requestFile);
        final HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.TEXT_HTML);


        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath(area);

        byte[] result = templateService.parseTemplateBytes(id, requestFile, new HashMap<String, Object>(), settings);
        // Get the specific Category from db
        // response.setContentType(ContentType.TEXT_HTML.toString());


        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }


       /* SimpleTemplate simplePage = pageService.findByName(name);
        if (null == simplePage) {
            throw new ResourceException.ResourceNotAvailable("Resource not Available", SimpleTemplate.TYPE_NAME, name);
        }
        if (simplePage.getVisibility() == PageVisibilityEnum.PUBLIC) {
            return prepareTemplate(simplePage.getContent());
        } else {
            return "NOT_PUBLIC";
        }          */
}


