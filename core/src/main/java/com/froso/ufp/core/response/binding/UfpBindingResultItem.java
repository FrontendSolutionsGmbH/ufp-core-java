package com.froso.ufp.core.response.binding;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: ck
 * Date: 11.12.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class UfpBindingResultItem {

    private String fieldName;
    private String message;
    private Object rejectedValue;

    /**
     * Gets field name.
     *
     * @return the field name
     */
    public String getFieldName() {

        return fieldName;
    }

    /**
     * Sets field name.
     *
     * @param fieldName the field name
     */
    public void setFieldName(String fieldName) {

        this.fieldName = fieldName;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {

        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Gets rejected value.
     *
     * @return the rejected value
     */
    public Object getRejectedValue() {

        return rejectedValue;
    }

    /**
     * Sets rejected value.
     *
     * @param rejectedValue the rejected value
     */
    public void setRejectedValue(Object rejectedValue) {

        this.rejectedValue = rejectedValue;
    }
}
