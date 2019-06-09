package com.froso.ufp.core.exceptions;

public class ResourceException extends UFPRuntimeException {

    private final String resourceType;
    private final String resourceId;

    /**
     * Constructor Resource exception.
     *
     * @param message        the message
     * @param resourceTypeIn the resource type in
     * @param resourceIdIn   the resource id in
     */
    public ResourceException(String message,
                             String resourceTypeIn,
                             String resourceIdIn) {

        super(message + " Resource: " + resourceTypeIn + " Id: " + resourceIdIn);
        resourceId = resourceIdIn;
        resourceType = resourceTypeIn;
    }

    /**
     * Instantiates a new Resource exception.
     *
     * @param message        the message
     * @param resourceTypeIn the resource type in
     * @param resourceIdIn   the resource id in
     * @param cause          the cause
     */
    public ResourceException(String message,
                             String resourceTypeIn,
                             String resourceIdIn,
                             Throwable cause) {

        super(message + " Resource: " + resourceTypeIn + " Id: " + resourceIdIn, cause);
        resourceId = resourceIdIn;
        resourceType = resourceTypeIn;
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

    /**
     * The type Resource not available.
     */
/*
    Sub-Exceptions
     */
    public static class ResourceNotAvailable extends ResourceException {
        /**
         * Constructor Resource not available.
         *
         * @param message        the message
         * @param resourceTypeIn the resource type in
         * @param resourceIdIn   the resource id in
         */
        public ResourceNotAvailable(String message,
                                    String resourceTypeIn,
                                    String resourceIdIn) {

            super(message, resourceTypeIn, resourceIdIn);
        }

        /**
         * Instantiates a new Resource not available.
         *
         * @param message        the message
         * @param resourceTypeIn the resource type in
         * @param resourceIdIn   the resource id in
         * @param cause          the cause
         */
        public ResourceNotAvailable(String message,
                                    String resourceTypeIn,
                                    String resourceIdIn,
                                    Throwable cause) {

            super(message, resourceTypeIn, resourceIdIn, cause);
        }
    }
}
