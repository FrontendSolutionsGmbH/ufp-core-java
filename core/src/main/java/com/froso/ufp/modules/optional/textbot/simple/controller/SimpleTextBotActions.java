package com.froso.ufp.modules.optional.textbot.simple.controller;

import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.textbot.simple.model.*;
import com.froso.ufp.modules.optional.textbot.simple.service.*;
import java.util.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
@TextBotAnnotation
public class SimpleTextBotActions {
    @Autowired
    SimpleTextBotService simpleTextBotService;

    @TextBotMethodAnnotation(regex = ".*", description = "Simple Text Bot Handler", command = "hallo", hidden = true)
    public TextBotStringResult textBotHandler(String message, Map<String, Object> templateData) {

        // check if incoming string matches anything
        List<SimpleTextBotModel> simpleTextBotModel = simpleTextBotService.findByKeyValue("attractionKeys", message);
        // just take first found
        if (!simpleTextBotModel.isEmpty()) {

            // return a random string from the options
            List<String> answers = simpleTextBotModel.get(0).getAnswers();
            if (answers.size() == 1) {

                return new TextBotStringResult(answers.get(0));
            } else if (answers.size() > 1) {

                return new TextBotStringResult(answers.get(RandomUtils.nextInt(0,answers.size() - 1)));
            }
        }

        return null;

    }
}
