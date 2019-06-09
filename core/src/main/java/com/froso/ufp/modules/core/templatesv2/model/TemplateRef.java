package com.froso.ufp.modules.core.templatesv2.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;

/**
 * Created by ck on 20.04.2016.
 * <p>
 * the template ref serves object for defining a template entry, template props can be injected with that, but foremost
 * the selection of which template to use and what entry in that template is used for rendering is defined here
 */
public class TemplateRef {


    private DataDocumentLink<FileTemplate> templateLink = new DataDocumentLink<>();
    private String entry = null;
    private String area = null;
    private String containerEntry = null;
    private PropsManager templateProps = new PropsManager();

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public PropsManager getTemplateProps() {
        return templateProps;
    }

    public void setTemplateProps(PropsManager templateProps) {
        this.templateProps = templateProps;
    }

    public DataDocumentLink<FileTemplate> getTemplateLink() {
        return templateLink;
    }

    public void setTemplateLink(DataDocumentLink<FileTemplate> templateLink) {
        this.templateLink = templateLink;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getContainerEntry() {
        return containerEntry;
    }

    public void setContainerEntry(String containerEntry) {
        this.containerEntry = containerEntry;
    }
}
