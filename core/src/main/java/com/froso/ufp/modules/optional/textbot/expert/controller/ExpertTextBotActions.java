package com.froso.ufp.modules.optional.textbot.expert.controller;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.textbot.expert.model.*;
import com.froso.ufp.modules.optional.textbot.expert.service.*;
import java.util.*;
import java.util.regex.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
@TextBotAnnotation
public class ExpertTextBotActions {
    //    @Autowired
//    private ExtendedTextBotService expertTextBotService;

    /**
     * The constant EXPERT_REGEX.
     */
    public static final String EXPERT_REGEX = "expert *(\\(.*\\))?.*";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpertTextBotActions.class);
    @Autowired
    private ExpertTextBotService expertTextBotService;

    /**
     * Text bot handler text bot string result.
     *
     * @param message      the message
     * @param templateData the template data
     * @return the text bot string result
     */
    @TextBotMethodAnnotation(regex = EXPERT_REGEX,
            description = "Expert Text Bot",
            command = "expert",
            name = "Expert",
            hidden = true)
    public TextBotStringResult expertBotHandler(String message, Map<String, Object> templateData) {
        Pattern pattern = Pattern.compile(EXPERT_REGEX);
        Matcher m = pattern.matcher(message);
        ExpertTextBot model = null;
        while (m.find()) {
            LOGGER.info("Expertbot groupcount : " + m.groupCount());
            LOGGER.info("Expertbot groupcount : " + m.group(0));
            LOGGER.info("Expertbot groupcount : " + m.group(1));
            if (m.group(1) != null) {
                LOGGER.info("Expertbot param: " + m.group(1));
                model = expertTextBotService.findOneBrute(m.group(1).substring(1, m.group(1).length() - 1));
            } else {
                // return rood
                model = expertTextBotService.findOneByKeyValue("type", ExpertbotNodeStatus.ROOT.toString());
            }
        }
        if (model != null) {

            if (model.getButtonType() == ExpertbotButtonType.QUICK_BUTTON) {
                TextBotQuickButtonResult result = new TextBotQuickButtonResult();
                result.setText(model.getText());
                if (model.getOptions() != null) {
                    for (DataDocumentLink<ExpertTextBot> expertRef : model.getOptions()) {
                        ExpertTextBot refModel = expertTextBotService.findOneBrute(expertRef.getId());
                        if (refModel != null) {
                            TextBotQuickButton quickButton = TextBotQuickButton.createQuickTextButton(refModel.getButtonText(), "expert (" + refModel.getId() + ")");
                            result.getQuick_replies().add(quickButton);
                        }
                    }
                    return result;
                }
            } else {
                // use "normal" button template (with url buttons)
                TextBotButtonResult result = new TextBotButtonResult();
                result.setText(model.getText());
                if (model.getOptions() != null) {
                    for (DataDocumentLink<ExpertTextBot> expertRef : model.getOptions()) {
                        ExpertTextBot refModel = expertTextBotService.findOneBrute(expertRef.getId());
                        if (refModel != null) {
                            TextBotButton quickButton = TextBotButton.createPostbackButton(refModel.getButtonText(), "expert (" + refModel.getId() + ")");
                            result.getButtons().add(quickButton);
                        }
                    }
                }
                return result;
            }
        }
        return null;
    }
}
