package com.froso.ufp.modules.core.templatesv1.service;

import com.froso.ufp.core.service.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.velocity.ui.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.textcomponent.service.*;
import org.apache.commons.lang3.*;
import org.apache.velocity.*;
import org.apache.velocity.app.*;
import org.apache.velocity.tools.generic.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

/**
 * The type Template parser service.
 */
@Service
public class TemplateParserService {

    private static final String PROP_NAME_TEMPLATES_GLOBALS_CONTACT_EMAIL = "templates.globals.contactEmail";
    private static final String PROP_NAME_TEMPLATES_GLOBALS_HOMEPAGE = "templates.globals.homepage";
    private static final String PROP_NAME_TEMPLATES_GLOBALS_ADRESS_EXTENSION = "templates.globals.adressExtension";
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateParserService.class);
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired(required = false)
    private TextComponentValueService valueService;

    @Autowired
    private IPropertyService propertyService;

    /**
     * could be factored out to a pure velocity service :/
     *
     * @param message the message
     * @return string string
     */
    public String parseMessage(VelocityMessage message) {

        return parseTemplate(message.getVelocityTemplatePath(), message.getData());

    }

    /**
     * Parse template string.
     *
     * @param template the template
     * @param props    the props
     * @return the string
     */
    public String parseTemplate(String template, Map<String, Object> props) {
        String result = StringUtils.EMPTY;

        try {

            LOGGER.info("INPUT FOR VELOCITY ENGINE:" + velocityEngine);
            LOGGER.info("INPUT FOR VELOCITY ENGINE:" + template);
            LOGGER.info("INPUT FOR VELOCITY ENGINE user " + props.get("user"));
            LOGGER.info("INPUT FOR VELOCITY ENGINE user " + props.get("activationUrl"));

            Map<String, Object> workProps = new HashMap<>(props);
            workProps.put("esc", new EscapeTool());
            workProps.put("date", new VelocityDateFormatter());
            workProps.put("alt", new AlternatorTool());
            workProps.put("display", new DisplayTool());
            workProps.put("math", new MathTool());
            workProps.put("loop", new LoopTool());
            workProps.put("link", new LinkTool());
            workProps.put("number", new NumberTool());
            workProps.put("sort", new SortTool());
            workProps.put("esc", new EscapeTool());
            workProps.put("price", new VelocityPriceFormatter());

            // make a simple
            Boolean shallbreak = Boolean.FALSE;
            Integer count = 0;
            workProps.putAll(createGlobals());
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", workProps);

            if (valueService != null) {
                while ((!shallbreak) && (count++ < 10)) {

                    // Call replacer for text components
                    result = valueService.replaceTextComponents(result, workProps, velocityEngine);
                    StringWriter writer = new StringWriter();
                    velocityEngine.evaluate(new VelocityContext(workProps), writer, "REPEAT  TEMPLATE", result);

                    shallbreak = !result.contains("$");

                }
            }
        } catch (Exception e) {
            result = e.getMessage();
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    private Map<String, Object> createGlobals() {
        // Add various static data to the template input
        Map<String, Object> globals = new HashMap<>();
        globals.put("contactEmail", propertyService.getPropertyValue(PROP_NAME_TEMPLATES_GLOBALS_CONTACT_EMAIL));
        globals.put("adressExtension", propertyService.getPropertyValue(PROP_NAME_TEMPLATES_GLOBALS_ADRESS_EXTENSION));
        globals.put("homepage", propertyService.getPropertyValue(PROP_NAME_TEMPLATES_GLOBALS_HOMEPAGE));
        return globals;
    }

}
