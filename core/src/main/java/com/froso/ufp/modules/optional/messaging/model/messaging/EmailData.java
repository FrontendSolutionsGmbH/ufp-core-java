package com.froso.ufp.modules.optional.messaging.model.messaging;

import com.froso.ufp.core.domain.documents.simple.validation.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;
import java.util.*;

public class EmailData extends MessageDataBase {

    @Indexed
    @Pattern(regexp = Validations.REGEXP_EMAIL)
    private String to;
    @Indexed
    private String subject;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getAttachmentMediaIDs() {
        return attachmentMediaIDs;
    }

    public void setAttachmentMediaIDs(List<String> attachmentMediaIDs) {
        this.attachmentMediaIDs = attachmentMediaIDs;
    }

    @Indexed
    private String body;
    private List<String> attachmentMediaIDs = new ArrayList<>();

}
