package com.froso.ufp.modules.optional.facebookbot.controller;

import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.facebookbot.service.*;
import java.lang.reflect.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 16.08.2016.
 */
@Component
@TextBotAnnotation
public class BotHelpController {
    /**
     * The constant PROPERTY_TEMPLATE_HELP_VM.
     */
    public static final String PROPERTY_TEMPLATE_HELP_VM = "template_help.vm";
    @Autowired
    private FacebookBotMethodService facebookMethodService;
    @Autowired
    private TemplateService templateService;

    /**
     * Helper bot text bot string result.
     *
     * @param message the message
     * @param data    the data
     * @return the text bot string result
     */
    @TextBotMethodAnnotation(regex = "(?i)help",
            description = "prints a list of available commands",
            name = "help")
    public TextBotStringResult helperBot(String message, Map<String, Object> data) {

        List<TextBotMethodAnnotation> methods = new ArrayList<>();
        for (Method method : facebookMethodService.getMethods()) {

            if (method.isAnnotationPresent(TextBotMethodAnnotation.class)) {
                TextBotMethodAnnotation ta = method.getAnnotation(TextBotMethodAnnotation.class);
                // LOGGER.info(ta.regex());
                methods.add(ta);
            }
        }
        Map<String, Object> dataNew = new HashMap<>();
        dataNew.putAll(data);
        dataNew.put("commands", methods);

        return new TextBotStringResult(parseSMSTemplate(dataNew, PROPERTY_TEMPLATE_HELP_VM));
    }

    /**
     * Test postback text bot button result.
     *
     * @param message the message
     * @param data    the data
     * @return the text bot button result
     */
    @TextBotMethodAnnotation(regex = "buttons",
            description = "test buttons server result",
            name = "postback test")
    public TextBotButtonResult testButtonsResult(String message, Map<String, Object> data) {


        TextBotButtonResult result = new TextBotButtonResult("what can we do you for?");
        result.getButtons().add(TextBotButton.createPostbackButton("Help", "help"));
        result.getButtons().add(TextBotButton.createPostbackButton("Test Buttons", "buttons"));
        result.getButtons().add(TextBotButton.createPostbackButton("Test QuickReply Buttons", "quickreply"));
        result.getButtons().add(TextBotButton.createUrlButton("FroSo Homepage", "http://www.froso.de"));
        return result;
    }

    /**
     * Test postback text bot button result.
     *
     * @param message the message
     * @param data    the data
     * @return the text bot button result
     */
    @TextBotMethodAnnotation(regex = "quickreply",
            description = "test quick reply server result",
            name = "postback test")
    public TextBotQuickButtonResult testQuickreplyResult(String message, Map<String, Object> data) {


        TextBotQuickButtonResult result = new TextBotQuickButtonResult("what can we do you for?");
        result.getQuick_replies().add(TextBotQuickButton.createQuickTextButton("Help", "help"));
        result.getQuick_replies().add(TextBotQuickButton.createQuickTextButton("Test Buttons", "buttons"));
        result.getQuick_replies().add(TextBotQuickButton.createQuickTextButton("Test QuickReply Buttons", "quickreply"));
        return result;
    }


    /**
     * Parse sms template string.
     *
     * @param data     the data
     * @param template the template
     * @return the string
     */
    String parseSMSTemplate(Map<String, Object> data, String template) {


        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("facebookbot");

        byte[] result = templateService.parseTemplateBytes("Default", template, data, settings);
        return new String(result);

    }

}
