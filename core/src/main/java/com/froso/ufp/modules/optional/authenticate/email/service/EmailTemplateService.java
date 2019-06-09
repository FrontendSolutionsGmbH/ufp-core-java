package com.froso.ufp.modules.optional.authenticate.email.service;

import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.optional.authenticate.email.model.*;
import org.springframework.stereotype.*;

@Service
public class EmailTemplateService {

    private TemplateSettings getHTMLWebContentTemplate(EmailAuthenticateModel model, String template) {

        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("email");
        settings.setLayoutMain("html-layoyt.vm");

        return settings;

    }

}
