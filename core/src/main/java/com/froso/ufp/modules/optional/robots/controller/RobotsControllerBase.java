package com.froso.ufp.modules.optional.robots.controller;

import com.froso.ufp.modules.core.templatesv1.model.*;
import com.froso.ufp.modules.core.templatesv1.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * provides a simple mechanism to read out a predefined robots.txt from the ressources path,
 * mapped to the default robots location of the api root
 *
 * Created by ckleinhuix on 25.05.2015.
 */
public class RobotsControllerBase {
    @Autowired
    private SimpleTemplateService templateService;

    /**
     * Main entry.
     *
     * @param request the request
     * @return the resource group
     */
    @RequestMapping(value = "/robots.txt", method = RequestMethod.GET)
    @ResponseBody
    public String mainEntry() {

        TemplateV1 templateV1 = templateService.findByPath("robots.txt");
        return templateV1.getContent();
    }
}
