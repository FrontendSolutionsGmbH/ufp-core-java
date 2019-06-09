package com.froso.ufp.modules.optional.messaging.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.templatesv1.service.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * The type Email sender service.
 */
@Service
public class EmailSenderService {

    private static final String PROP_NAME_TEMPLATES_GLOBALS_CONTACT_EMAIL = "templates.globals.contactEmail";
    private static final String PROP_NAME_TEMPLATES_GLOBALS_HOMEPAGE = "templates.globals.homepage";
    private static final String PROP_NAME_TEMPLATES_GLOBALS_ADRESS_EXTENSION = "templates.globals.adressExtension";
    @Autowired(required = false)
    private TemplateParserService templateParserService;
    @Autowired
    private QueueWorkerEmailSender senderQueueWorker;
    @Autowired
    private QueueEmailService emailLogService;

    @Autowired
    private IPropertyService propertyService;

    public String parseMessageXXX(VelocityMessage message) {

        return templateParserService.parseMessage(message);

    }

    public String parseTemplateXXX(String template, Map<String, Object> props) {
        return templateParserService.parseTemplate(template, props);
    }

    public QueueEmail sendMail(String to, String subject, String body) {

        return sendMailWrapped(to, subject, body, null);
    }

    public QueueEmail sendMail(String to, String subject, String body, String coreUserId) {

        return sendMailWrapped(to, subject, body, coreUserId);
    }

    private QueueEmail sendMailWrapped(String to, String subject, String body, String coreUserId) {

        QueueEmail emailData = new QueueEmail();
        if (null != coreUserId) {
            emailData.setCoreUser(new DataDocumentLink<>(coreUserId));
        }

        emailData.getMessageData().setText(body);
        emailData.getMessageData().setTo(to);
        emailData.getMessageData().setSubject(subject);
        return emailLogService.save(emailData);
    }

    public QueueEmail sendHTMLVelocityTemplateEmailDirect(String to, String subject, String template, Map data) {
        return sendHTMLVelocityTemplateEmailDirect(to, subject, template, data, new ArrayList<String>());
    }

    public QueueEmail sendHTMLVelocityTemplateEmailDirect(String to, String subject, String template, Map data, List<String> attachmentMediaIDs) {

        QueueEmail result = sendHTMLVelocityTemplateEmail(to, subject, template, data, attachmentMediaIDs);
        senderQueueWorker.sendEmail(result);
        return result;
    }

    public QueueEmail sendHTMLVelocityTemplateEmail(String to, String subject, String template, Map data, List<String> attachmentMediaIDs) {


        Map<String, Object> props = new HashMap<>();//m.convertValue(data, Map.class);
        props.putAll(data);
        // Add various static data to the template input
        props.put("globals", createGlobals());
        QueueEmail emailData = getSimpleEmail(to, subject, template, props, emailLogService, attachmentMediaIDs);
        return emailData;
        // send emails directly from error
    }

    private Map<String, Object> createGlobals() {
        // Add various static data to the template input
        Map<String, Object> globals = new HashMap<>();
        globals.put("contactEmail", propertyService.getPropertyValue(PROP_NAME_TEMPLATES_GLOBALS_CONTACT_EMAIL));
        globals.put("adressExtension", propertyService.getPropertyValue(PROP_NAME_TEMPLATES_GLOBALS_ADRESS_EXTENSION));
        globals.put("homepage", propertyService.getPropertyValue(PROP_NAME_TEMPLATES_GLOBALS_HOMEPAGE));
        return globals;
    }

    public QueueEmail getSimpleEmail(final String to, final String subject, final String template, final Map<String, Object> props, final QueueEmailService messageEmailService, List<String> attachmentMediaIDs) {

        String body = templateParserService.parseTemplate(template, props);

        QueueEmail emailData = new QueueEmail();
        emailData.getMessageData().setText(body);
        emailData.getMessageData().setTo(to);

        // email receivers need to be valid users ...
       /* try {
            CoreUser coreUser = userService.findUserByEmailWithoutValidation(to);
            emailData.getReceiverUser().setId(coreUser.getId());
        } catch (Exception e) {

            // problem with email user
            LOGGER.error("Send email exception ", e);
            // not important to set receiver id anyways --- but if there link user to it

        }  */
        emailData.getMessageData().setSubject(subject);
        VelocityMessage velocityMessage = new VelocityMessage();
        velocityMessage.setData(props);
        velocityMessage.setVelocityTemplatePath(template);
        emailData.setVelocityMessage(velocityMessage);
        emailData.getMessageData().setAttachmentMediaIDs(attachmentMediaIDs);
        messageEmailService.save(emailData);
        return emailData;
    }

    public QueueEmail sendHTMLVelocityTemplateEmail(String to, String subject, String template, Map data) {
        return sendHTMLVelocityTemplateEmail(to, subject, template, data, new ArrayList<String>());
    }


}
