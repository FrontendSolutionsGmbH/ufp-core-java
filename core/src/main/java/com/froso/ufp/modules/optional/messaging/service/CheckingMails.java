package com.froso.ufp.modules.optional.messaging.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.messaging.configuration.*;
import com.froso.ufp.modules.optional.messaging.model.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.*;
import org.joda.time.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class CheckingMails {

    public static final String PROP_START_DATE = "receivemail.receiveStartDate";
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckingMails.class);
    @Autowired
    private IPropertyService propertyService;
    @Autowired(required = false)
    private ReceivedEmailsService receivedEmailsService;


    private SearchTerm getSearchTerm(Date minDate) {

        SearchTerm term = null;

        Calendar cal = null;
        cal = Calendar.getInstance();
        Date maxDate = new Date(cal.getTimeInMillis());   //get today date

        //  cal.add(Calendar.DAY_OF_MONTH, 1);                //add 1 day
        //  Date maxDate = new Date(cal.getTimeInMillis());   //get tomorrow date
        SentDateTerm minDateTerm = new SentDateTerm(ComparisonTerm.GE, minDate);
        SentDateTerm maxDateTerm = new SentDateTerm(ComparisonTerm.LE, maxDate);

        //  term = new AndTerm(term, minDateTerm);            //concat the search terms
        term = new AndTerm(minDateTerm, maxDateTerm);

        //  Message messages[] = folderInbox.search(term);    //search on the imap server
        return term;
    }

    public void check(String host, String user,
                      String password) {
        try {
            if (receivedEmailsService == null) {

                LOGGER.error("ReceivedEmailsService not set");
                return;
            }
            ReceivedEmail latest = receivedEmailsService.findOneByKeyValue("sort", "-dateReceived");

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            //  properties.put("mail.pop3.port", "995");
            //  properties.put("mail.pop3.starttls.enable", "true");
            SearchTerm searchTerm = null;
            if (latest != null) {
                LOGGER.info("Last stored email is " + latest.getMetaData().getCreationTimestamp().toString());
                searchTerm = getSearchTerm(latest.getDateReceived().toDate());
            } else {
                searchTerm = getSearchTerm(org.joda.time.LocalDate.parse(propertyService.getPropertyValue(PROP_START_DATE)).toDate());
            }
            properties.setProperty("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            Message[] messages = null;
            if (searchTerm != null) {
                messages = emailFolder.search(searchTerm, emailFolder.getMessages());
            } else {
                messages = emailFolder.getMessages();
            }


            LOGGER.info("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                LOGGER.info("---------------------------------");
                LOGGER.info("Email Number " + (i + 1));
                LOGGER.info("recipients: " + message.getAllRecipients().toString() + "");
                LOGGER.info("from: " + message.getFrom().toString() + "");
                LOGGER.info("messagenumber: " + message.getMessageNumber() + "");
                LOGGER.info("Subject: " + message.getSubject());
                LOGGER.info("From: " + message.getFrom()[0].toString());
                LOGGER.info("Text: " + message.getContent().toString());
                LOGGER.info("Text: " + message.toString());
                LOGGER.info("sent: " + message.getSentDate() + "");
                LOGGER.info("received: " + message.getReceivedDate() + "");
                LOGGER.info("received: " + message.getContentType() + "");

                Object content = message.getContent();

                ReceivedEmail receivedEmail = receivedEmailsService.getDefault();
                receivedEmail.setContentType(message.getContentType());
                if (message.getContentType().contains("multipart")) {
                    if (content instanceof MimeMultipart) {
// multiparts converting to local parts
                        MimeMultipart mimeMultipart = (MimeMultipart) content;
                        for (int k = 0; k < mimeMultipart.getCount(); k++) {

                            BodyPart bodyPart = mimeMultipart.getBodyPart(k);


                            receivedEmail.getContent().add(new ContentElement(bodyPart.getContentType(), bodyPart.getContent().toString()));


                        }
                    } else {
                        // dunno what else might be returned here, classify it as default tostring, will resolved later on when we see what happens
                        receivedEmail.getContent().add(new ContentElement(message.getContentType(), message.getContent().toString()));

                    }
                } else {
                    // default just tostring and store with mime type
                    receivedEmail.getContent().add(new ContentElement(message.getContentType(), message.getContent().toString()));

                }

                receivedEmail.setSubject(message.getSubject());
                //   receivedEmail.setBody(message.getContent().toString());

                if (message.getReceivedDate() != null) {
                    receivedEmail.setDateReceived(new DateTime(message.getReceivedDate()));
                }
                if (message.getSentDate() != null) {
                    receivedEmail.setDateSend(new DateTime(message.getSentDate()));
                }
                Enumeration<Header> headers = message.getAllHeaders();


                while (headers.hasMoreElements()) {

                    Header next = headers.nextElement();

                    receivedEmail.getHeaders().put(next.getName(), next.getValue());


//                    String param = (String) headers.nextElement();
                }

                for (Address adressData : message.getAllRecipients()) {
                    receivedEmail.getReceiver().add(adressData.toString());
                }
                for (Address adressData : message.getFrom()) {
                    receivedEmail.getFrom().add(adressData.toString());
                }

                if (latest != null) {
                    // double check the date, imap searching just accepts dates, not times ...
                    if (latest.getDateReceived().isBefore(receivedEmail.getDateReceived())) {

                        receivedEmailsService.save(receivedEmail);
                    }
                } else {

                    receivedEmailsService.save(receivedEmail);
                }


            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main() {

        String host = propertyService.getPropertyValue(EmailConfiguration.SENDMAIL_HOST);// change accordingly
        String username = propertyService.getPropertyValue(EmailConfiguration.SENDMAIL_USERNAME);// change accordingly
        String xword = propertyService.getPropertyValue(EmailConfiguration.SENDMAIL_PASSWORD);// change accordingly

        check(host, username, xword);

    }

}
