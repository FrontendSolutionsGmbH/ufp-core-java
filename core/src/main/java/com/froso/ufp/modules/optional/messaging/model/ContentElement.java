package com.froso.ufp.modules.optional.messaging.model;

/**
 * Created by ckleinhuis on 26.10.2016.
 */
public class ContentElement {

    private String contentType;
    private String content;

    public ContentElement() {
    }

    public ContentElement(String cType, String c) {
        this.content = c;
        this.contentType = cType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
