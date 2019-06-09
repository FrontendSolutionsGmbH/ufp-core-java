package com.froso.ufp.modules.optional.textbot.extended.controller;

import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.textbot.extended.model.*;
import com.froso.ufp.modules.optional.textbot.extended.service.*;
import java.util.*;
import java.util.regex.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
@TextBotAnnotation
public class ExtendedTextBotActions {
//    @Autowired
//    private ExtendedTextBotService extendedTextBotService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedTextBotActions.class);
    @Autowired
    private ExtendedTextBotService extendedTextBotService;


    /**
     * Text bot handler text bot string result.
     *
     * @param message      the message
     * @param templateData the template data
     * @return the text bot string result
     */
    @TextBotMethodAnnotation(regex = ".*",
            description = "Extended Text Bot Handler",
            command = "",
            hidden = true)
    public TextBotStringResult textBotHandler(String message, Map<String, Object> templateData) {
        String answer = null;
        List<ExtendedTextBot> defs = extendedTextBotService.findAll();
        for (ExtendedTextBot model : defs) {
            Pattern pattern = Pattern.compile(model.getRegex());
            Matcher m = pattern.matcher(message);
            String theUrl = model.getUrlToCall();
            if (m.matches()) {            //   if (m.matches()) {
                answer = model.getAnswer();
                // replace all match groups $1....
                if (answer != null) {
                    try {
                        answer = m.replaceAll(answer);
                    } catch (Exception e) {
                        LOGGER.error("extended textbot error",e);
                    }
                }
                if (theUrl != null) {
                    try {
                        theUrl = m.replaceAll(theUrl);
                    } catch (Exception e) {
                        LOGGER.error("extended textbot error",e);
                    }
                }
                // just call the resulting url
                HttpUriRequest request = null;
                if (model.getMethod().equals("get")) {
                    request = new HttpGet(theUrl);
                } else if (model.getMethod().equals("post")) {
                    request = new HttpPost(theUrl);
                }
                try {
                    HttpClient client = HttpClientBuilder.create().build();
                    client.execute(request);
                } catch (Exception e) {
                    LOGGER.error("Extended Text Bot error ", e);
                }
            }
            //    }
        }
        if (answer != null) {
            return new TextBotStringResult(answer);
        } else {
            return null;
        }
    }
}
