package com.froso.ufp.modules.optional.messaging.service;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.configuration.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.core.worker.annotations.*;
import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.media.model.*;
import com.froso.ufp.modules.optional.media.service.*;
import com.froso.ufp.modules.optional.messaging.configuration.*;
import com.froso.ufp.modules.optional.messaging.exception.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.mail.*;
import org.springframework.mail.javamail.*;
import org.springframework.scheduling.concurrent.*;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.annotation.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * The type Queue worker email sender.
 */
@EnableWebSocket
@Service
public class QueueWorkerEmailSender implements ServiceStatusReporter, INamedObject {

    public static final String PROP_NAME_MAX_RETRY_COUNT = "workflow.maxRetries";
    public static final String PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP = "workflow.maxEntriesToProcessInOneStep";
    public static final String PROPERTY_WORKFLOW_MAXTHREADS = "workflow.maxthreads";
    public static final String PROP_NAME_SEND_EMAILS_ENABLED = "workflow.email.enabled";
    public static final String PROP_NAME_SENDMAIL_FROM = "sendmail.from";
    public static final String PROP_NAME_SENDMAIL_DEBUG = "sendmail.debug";
    public static final String PROP_NAME_SENDMAIL_ADMINEMAIL = "sendmail.adminemail";
    public static final String PROP_NAME_SENDMAIL_SMTP_AUTH = "sendmail.smtp.auth";
    public static final String PROP_NAME_SENDMAIL_SMTP_STARTTLS = "sendmail.smtp.starttls";
    public static final String PROP_NAME_SENDMAIL_TRANSPORT_PROTOCOL = "sendmail.transport.protocol";
    public static final String NAME = "EmailSenderService";
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueWorkerEmailSender.class);
    private final AtomicBoolean processingEmails = new AtomicBoolean(false);
    @Autowired
    GlobalsService globalsService;
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private QueueEmailService messageEmailService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired(required = false)
    private PictureService mediaService;
    @Autowired(required = false)
    private IPropertyService propertyService;

    private JavaMailSenderImpl getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(propertyService.getPropertyValue(EmailConfiguration.SENDMAIL_HOST));
        mailSender.setPort(propertyService.getPropertyValueInteger(EmailConfiguration.SENDMAIL_PORT));
        mailSender.setPassword(propertyService.getPropertyValue(EmailConfiguration.SENDMAIL_PASSWORD));
        mailSender.setUsername(propertyService.getPropertyValue(EmailConfiguration.SENDMAIL_USERNAME));
        return mailSender;
    }

    @WorkerAnnotation(description = "Works through email queue and processes them", name = "QueueWorkerEmailSender")
    public void sendNewEmails() {

        if (!propertyService.getPropertyValueBoolean(PROP_NAME_SEND_EMAILS_ENABLED)) {
            return;
        }
        if (!processingEmails.get()) {
            processingEmails.set(true);
            Runnable t = new Runnable() {
                @Override
                public void run() {

                    Pageable pageable = new PageRequest(0, propertyService.getPropertyValueInteger(PROPERTY_WORKFLOW_MAX_ENTRIES_TO_PROCESS_IN_ONE_STEP), Sort.Direction.ASC, AbstractDataDocument.META_DATA_LAST_CHANGED_TIMESTAMP);
                    List<QueueEmail> workflowInstances = messageEmailService.findAllNew(pageable);
                    List<QueueEmail> workflowInstancesError = messageEmailService.findAllErrornous(pageable);
                    // Add the errornous as well
                    workflowInstances.addAll(workflowInstancesError);
                    Parallel.parallelFor(workflowInstances, propertyService.getPropertyValueInteger(PROPERTY_WORKFLOW_MAXTHREADS), "EmailSending", new ParallelStep() {
                        @Override
                        public boolean execute(int index, List<?> list) {

                            QueueEmail simpleMessageEmail = (QueueEmail) list.get(index);
                            LOGGER.debug("Processing:" + simpleMessageEmail);
                            sendEmail(simpleMessageEmail);
                            return true;
                        }
                    });
                    processingEmails.set(false);
                }
            };
            threadPoolTaskExecutor.execute(t);
        }
    }
//
//    @PostConstruct
//    private void initialise(
//
//    ) {
//
//
//        //   globalsService.addProperty("adminEmail", QueueWorkerEmailSender.PROP_NAME_SENDMAIL_ADMINEMAIL);
//    }

    public void sendEmail(QueueEmail simpleMessageEmail) {

        try {
            sendHTMLMailWrapped(simpleMessageEmail.getMessageData().getTo(), simpleMessageEmail.getMessageData().getSubject(), simpleMessageEmail.getMessageData().getText(), simpleMessageEmail.getMessageData().getAttachmentMediaIDs());
            // And Send a copy to admin as well...
            if (propertyService.getPropertyValueBoolean(PROP_NAME_SENDMAIL_DEBUG)) {
                sendHTMLMailWrapped(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL), "DEBUG " + simpleMessageEmail.getMessageData().getSubject(), simpleMessageEmail.getMessageData().getText(), simpleMessageEmail.getMessageData().getAttachmentMediaIDs());
            }
            simpleMessageEmail.setStatus(MessageStatusEnum.PROCESSED);
        } catch (Exception e) {
            LOGGER.trace("Email Error" + e.getMessage(), e);
            simpleMessageEmail.setErrorMessage(e.getMessage());
            simpleMessageEmail.setRetryCount(simpleMessageEmail.getRetryCount() + 1);
            if (simpleMessageEmail.getRetryCount() > propertyService.getPropertyValueInteger(PROP_NAME_MAX_RETRY_COUNT)) {
                simpleMessageEmail.setStatus(MessageStatusEnum.ERROR_CANCELLED);
            } else {
                simpleMessageEmail.setStatus(MessageStatusEnum.ERROR);
            }
        }
        messageEmailService.save(simpleMessageEmail);
    }

    public void sendHTMLMailWrapped(String to, String subject, String body, List<String> attachmentIDs) throws EmailException {

        if (!("".equals(to))) {
            JavaMailSenderImpl mailSender = getJavaMailSender();
            mailSender.setJavaMailProperties(getMailProperties());
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setSubject(subject);
                mimeMessage.setContent(body, "text/html");

                //-------- resolve media attachments
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(new InternetAddress(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_FROM)));
                helper.setTo(new InternetAddress(to));
                helper.setSubject(subject);
                helper.setText(HtmlUtil.htmlExtractUnescapedTextOnly(body), body);

                try {

                    for (String mediaID : attachmentIDs) {
                        if (mediaService != null) {

                            FilePicture media = mediaService.findOne(mediaID);
                            if (media != null) {

                                // create media url loadings from resources and media host ...

                                helper.addAttachment(media.getMediaElement().getMediaHostFileName(), mediaService.getByArrayRessource(media));

//                            helper.addAttachment(media.getMediaElement().getMediaHostFileName(), new UrlResource(media.getMediaElement().getFullPath()));

                            }
                        }

                    }

                } catch (MessagingException e) {
                    throw new MailParseException(e);
                }

                //-------- media attachments end

                mailSender.send(mimeMessage);
            } catch (Exception e) {
                throw new EmailException(e.getMessage(), e);
            }
        }
    }

    private Properties getMailProperties() {

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.transport.protocol", propertyService.getPropertyValue(PROP_NAME_SENDMAIL_TRANSPORT_PROTOCOL));
        javaMailProperties.setProperty("mail.smtp.auth", propertyService.getPropertyValue(PROP_NAME_SENDMAIL_SMTP_AUTH) + "");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", propertyService.getPropertyValue(PROP_NAME_SENDMAIL_SMTP_STARTTLS + ""));
        javaMailProperties.setProperty("mail.debug", propertyService.getPropertyValue(PROP_NAME_SENDMAIL_DEBUG) + "");
        return javaMailProperties;
    }

    public void sendHTMLVelocityTemplateEmail(String to, String subject, String template, Object data, List<String> attachmentIDs) {

        Map<String, Object> props = objectMapper.convertValue(data, Map.class);
        QueueEmail emailData = emailSenderService.getSimpleEmail(to, subject, template, props, messageEmailService, attachmentIDs);

        messageEmailService.save(emailData);
        sendHTMLMail(to, subject, emailData.getMessageData().getText(), attachmentIDs);
    }

    private boolean sendHTMLMail(String to, String subject, String body, List<String> attachmentIDs) {

        boolean result = false;
        // send desired mail
        sendHTMLMailWrapped(to, subject, body, attachmentIDs);
        if (null != to && !to.equalsIgnoreCase(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL))) {
            // we want to send debug message to a given email
            sendHTMLMailWrapped(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL), "DEBUG" + subject + "-" + to, body, attachmentIDs);
            result = true;
        }
        return result;
    }

    public String getName() {

        return NAME;

    }

    @Override
    public ServiceStatus getServiceStatus() {

        ServiceStatus s = new ServiceStatus();
        //     s.setIsWorking(sendMail(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL), "Status Mail", "test test test"));
        //     s.addLogMessage("From: " + propertyService.getPropertyValue(PROP_NAME_SENDMAIL_FROM));
        //     s.addLogMessage("AdminMail: " + propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL));
        //     s.setName(TYPE_NAME);
        return s;
    }

    boolean sendMail(String to, String subject, String body) {

        boolean result = false;
        try {
            sendMailWrapped(to, subject, body);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Email Sending Error + Silently Consumed ", e);
        }
        if (null != to && !to.equalsIgnoreCase(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL))) {
            try {
                sendMailWrapped(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_ADMINEMAIL), "DEBUG" + subject + "-" + to, body);
            } catch (Exception e) {
                LOGGER.error("Email Sending Error + Silently Consumed ", e);
            }
        }
        return result;
    }

    private void sendMailWrapped(String to, String subject, String body) {
        JavaMailSenderImpl mailSender = getJavaMailSender();
        mailSender.setJavaMailProperties(getMailProperties());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(propertyService.getPropertyValue(PROP_NAME_SENDMAIL_FROM));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
