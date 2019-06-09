package com.froso.ufp.modules.optional.messaging.model.messaging;

import java.util.*;

public class VelocityMessage {

    private String velocityTemplatePath;
    //    private TemplateRef templateRef;
    private Map<String, Object> data;

    public String getVelocityTemplatePath() {
        return velocityTemplatePath;
    }

    public void setVelocityTemplatePath(String velocityTemplatePath) {
        this.velocityTemplatePath = velocityTemplatePath;
    }

//    public TemplateRef getTemplateRef() {
//        return templateRef;
//    }
//
//    public void setTemplateRef(TemplateRef templateRef) {
//        this.templateRef = templateRef;
//    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
