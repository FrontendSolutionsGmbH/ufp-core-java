package com.froso.ufp.modules.optional.textbot.bureaubot.controller;

import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.model.*;
import com.froso.ufp.modules.optional.textbot.bureaubot.service.*;
import java.net.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 21.06.2016.
 */
@Service
@TextBotAnnotation
public class BureauBotActions {
    @Autowired
    BureauBotService bureauBotService;

    @TextBotMethodAnnotation(regex = "(http|https|www\\.)", description = "Post an url, and it will be displayed", command = "any url", hidden = true)
    public TextBotStringResult textBotHandler(String message, Map<String, Object> templateData) {
        String contentType = getContentTypeFromUrl(message);

        BureauBot model = new BureauBot();
        model.setUrl(message);
        model.setContentType(contentType);
        bureauBotService.save(model);

        return new TextBotStringResult(" url received ");

    }

    @TextBotMethodAnnotation(regex = "alarm (\\d*)", description = "Direct Start alarm", command = "hallo", hidden = true)
    public TextBotStringResult textBotAlarm(String message, Map<String, Object> templateData) {
        String contentType = getContentTypeFromUrl(message);

        BureauBot model = new BureauBot();
        model.setUrl(message);
        model.setContentType(contentType);
        bureauBotService.save(model);

        return new TextBotStringResult(" alarm received ");

    }

    private String getContentTypeFromUrl(String urlIn) {
        try {
            URL url = new URL(urlIn);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            return contentType;
        } catch (Exception e) {
            return "unknown";
        }
    }
}
