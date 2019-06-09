package com.froso.ufp.core.service.util;

/**
 * pojp class defining foreign key relations, it is used in the ufp backend resource definitions, to inform
 * about incoming and outgoing foreign keys of a resource
 */
public class ForeignKey {

    private String resourceName;
    private String fieldName;

    public ForeignKey() {
    }

    public ForeignKey(String resourceName, String fieldName) {
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "ForeignKey{" +
                "resourceName='" + resourceName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
