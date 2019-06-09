package com.froso.ufp.modules.core.templatesv2.controller.optional;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import io.swagger.annotations.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@Controller
@Api(description = "Template Functionality",
        tags = FileTemplate.TYPE_NAME)
class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    private final TemplateService templateService;
    private final IPropertyService propertyService;

    public IndexController(TemplateService templateService, IPropertyService propertyService) {
        this.templateService = templateService;
        this.propertyService = propertyService;
    }

//
//    @RequestMapping(value = "/home/{fileName}.{suffix}",
//            method = RequestMethod.GET)
//    @ApiOperation(hidden = false,
//            value = "Display a particular home area template",
//            notes = "Helper method to allow linking template files on root level")
//
//    public ResponseEntity<byte[]> getTeamplate(HttpServletRequest request,
//
//                                               @PathVariable
//                                                       String fileName,
//                                               @PathVariable
//                                                       String suffix,
//                                               HttpServletResponse response) {
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_HTML);
//
//
//        TemplateSettings settings = new TemplateSettings();
//        settings.setTemplatePath("home");
//        HashMap<String, Object> data = new HashMap<>();
//
//
//        byte[] result = templateService.parseTemplateBytes("Default", fileName + ".vm", data, settings);
//        return new ResponseEntity<>(result, headers, HttpStatus.OK);
//    }

    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    @ApiOperation(hidden = false,
            value = "Entry point for the whole application",
            notes = "Display index.html Template, if found in static path this is returned, otherwise template, /ufp-root.html provides the templated index")

    public ResponseEntity<byte[]> getIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // check if index.html file exists in static root path
        String filenameIndexHtml = propertyService.getPropertyValue(WebConfigCore.PROPERTY_UFP_STATIC_RESOURCES_PATH) + "/index.html";

        if (Files.exists(Paths.get(filenameIndexHtml))) {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);

            byte[] result = Files.readAllBytes(Paths.get(filenameIndexHtml));

            return new ResponseEntity<>(result, headers, HttpStatus.OK);
        } else {

            return getIndexAlternative(request, response);
        }

    }

    @RequestMapping(value = "/ufp-root.html",
            method = RequestMethod.GET)
    @ApiOperation(hidden = false,
            value = "Display Index Template",
            notes = "This endpoint returns a rendered version of the /resources/templates/home/index.vm Template")

    public ResponseEntity<byte[]> getIndexAlternative(HttpServletRequest request, HttpServletResponse response) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("home");

        HashMap<String, Object> data = new HashMap<>();

        byte[] result = templateService.parseTemplateBytes("Default", "index.vm", data, settings);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }
}


