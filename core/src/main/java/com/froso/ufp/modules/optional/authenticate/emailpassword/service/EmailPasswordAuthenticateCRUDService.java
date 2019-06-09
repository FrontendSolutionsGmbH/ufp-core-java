package com.froso.ufp.modules.optional.authenticate.emailpassword.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.authenticate.service.*;
import com.froso.ufp.modules.core.templatesv2.model.*;
import com.froso.ufp.modules.core.templatesv2.service.*;
import com.froso.ufp.modules.optional.authenticate.emailpassword.model.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class EmailPasswordAuthenticateCRUDService extends AbstractCoreAuthenticationsService<AuthenticateEmailPassword> {

    /**
     * The Property service.
     */
    @Autowired
    IPropertyService propertyService;
    //    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private TemplateService templateService;

    /**
     * Constructor Simple product service.
     */
    public EmailPasswordAuthenticateCRUDService() {
        super(AuthenticateEmailPassword.TYPE_NAME);
    }

    protected void prepareSave(AuthenticateEmailPassword object) {
        // call any present super method
        super.prepareSave(object);

        // encode the received password using the password encoder
        // object.getData().setPassword(passwordEncoder.encode(object.getData().getPassword()));

    }

    /**
     * Parse UsernamePassword template string.
     *
     * @param model    the model
     * @param template the template
     * @return the string
     */
    String parseUsernamePasswordTemplate(AuthenticateEmailPassword model, String template) {


        TemplateSettings settings = new TemplateSettings();
        settings.setTemplatePath("UsernamePassword-auth");
        HashMap<String, Object> data = new HashMap<>();
        data.put("authUsernamePassword", model);

        byte[] result = templateService.parseTemplateBytes("Default", template, data, settings);
        return new String(result);

    }

}
