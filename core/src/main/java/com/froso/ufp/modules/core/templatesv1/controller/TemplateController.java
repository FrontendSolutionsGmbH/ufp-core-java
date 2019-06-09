package com.froso.ufp.modules.core.templatesv1.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.templatesv1.configuration.*;
import com.froso.ufp.modules.core.templatesv1.model.*;
import com.froso.ufp.modules.core.templatesv1.service.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * The type Template controller.
 */
@Controller
@RequestMapping("/" + TemplateV1.TYPE_NAME)
class TemplateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);
    /**
     * The Standard pages.
     */
    @Autowired
    @Qualifier(StandardPagesConfig.BEAN_STANDARD_PAGES)
    private StandardPagesMap standardPages;
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private SimpleTemplateService pageService;
    @Autowired
    private IPropertyService propertyService;

    /**
     * Gets page by full path.
     *
     * @param name     the name
     * @param request  the request
     * @param response the response
     * @return the page by full path
     */
    @RequestMapping(value = "/{name.+}", method = RequestMethod.GET)
    @ResponseBody
    public String getPageByFullPath(@PathVariable("name.+") String name, HttpServletRequest request, HttpServletResponse response) {
        ResponseHandler.setDefaultHeaders(response);

        LOGGER.debug("Page Requested with name:" + name);
        // Get the specific Category from db
        TemplateV1 simplePage = pageService.findByName(name);
        if (null == simplePage) {
            throw new ResourceException.ResourceNotAvailable("Resource not Available", TemplateV1.TYPE_NAME, name);
        }
        if (simplePage.getVisibility() == PageVisibilityEnum.PUBLIC) {
            return prepareTemplate(simplePage.getContent());
        } else {
            return "NOT_PUBLIC";
        }
    }

    private String prepareTemplate(String templateString) {

        Map<String, String> config = new HashMap<>();
        config.put("webPagesRoot", propertyService.getPropertyValue(UFPConstants.PROPERTY_APPLICATION_WEB) + "/");
        config.put("adressExtension", propertyService.getPropertyValue("templates.globals.adressExtension"));
        config.put("contactEmail", propertyService.getPropertyValue("templates.globals.contactEmail"));
        config.put("homepage", propertyService.getPropertyValue("templates.globals.homepage"));
        config.put("adminEmail", propertyService.getPropertyValue(QueueWorkerEmailSender.PROP_NAME_SENDMAIL_ADMINEMAIL));
        VelocityContext context = new VelocityContext();
        context.put("config", config);
        StringWriter result = new StringWriter();
        try {
            velocityEngine.evaluate(context, result, "pageoutput", templateString);
        } catch (Exception e) {
            LOGGER.warn("Page Template Parse Error", e);
        }
        return HtmlUtil.escapeHtmlTextOnly(result.toString());
    }

    /**
     * Gets corporate page by full path.
     *
     * @param name        the name
     * @param response    the response
     * @param corporateId the corporate id
     * @param request     the request
     * @return the corporate page by full path
     * @throws Exception the exception
     */
    @RequestMapping(value = "/{corporateId}/{name.+}", method = RequestMethod.GET)
    @ResponseBody
    public String getCorporatePageByFullPath(@PathVariable("name.+") String name, HttpServletResponse response, @PathVariable("corporateId") String corporateId, HttpServletRequest request) throws Exception {
        ResponseHandler.setDefaultHeaders(response);

        LOGGER.debug("Page Requested with name:" + name);
        // Get the specific Category from db
        TemplateV1 simplePage = pageService.findByNameAndCorporateId(name, corporateId);
        if (null == simplePage) {
            // Try Getting default variant of page
            simplePage = pageService.findByName(name);
            if (simplePage == null) {
                // Try to retrieve the page from classpath (default)
                if (null != standardPages.get(name)) {
                    // create templateString object
                    simplePage = new TemplateV1();
                    simplePage.setVisibility(PageVisibilityEnum.PUBLIC);
                    simplePage.setContent(FileUtil.getFile(standardPages.get(name)));
                }
            }
        }
        if (simplePage != null) {
            if (simplePage.getVisibility() == PageVisibilityEnum.PUBLIC) {
                return prepareTemplate(simplePage.getContent());
            } else {
                return "NOT_PUBLIC";
            }
        }
        return "NOT FOUND";
    }
}
