package com.froso.ufp.core.exceptions;

/**
 * Created with IntelliJ IDEA. SimpleUser: ckleinhuix Date: 01.12.13 Time: 14:00 To change this template use File |
 * Settings |
 * File Templates.
 */
public class MainResourceNotFoundException extends UFPRuntimeException {

    private final String resourceType;
    private final String resourceId;

    /**
     * Constructor Resource not found exception.
     *
     * @param resourceName the resource name
     * @param resourceId   the resource id
     */
    public MainResourceNotFoundException(String resourceName, String resourceId) {

        super("Resource " + resourceName + " with id: " + resourceId + " not found");
        this.resourceId = resourceId;
        this.resourceType = resourceName;
    }

    /**
     * Gets resource type.
     *
     * @return the resource type
     */
    public String getResourceType() {

        return resourceType;
    }

    /**
     * Gets resource id.
     *
     * @return the resource id
     */
    public String getResourceId() {

        return resourceId;
    }
}
