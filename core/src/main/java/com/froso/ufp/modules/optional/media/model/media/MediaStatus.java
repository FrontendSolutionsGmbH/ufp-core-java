package com.froso.ufp.modules.optional.media.model.media;

/**
 * Created by ckleinhuix on 14.03.2015.
 * <p>
 * the status fields define status of a media element
 */
public enum MediaStatus {
    /**
     * Unprocessed media status.
     */
    UNPROCESSED,
    /**
     * Processed media status.
     */
    PROCESSED,
    /**
     * Not available on media host media status.
     */
    NOT_AVAILABLE_ON_MEDIA_HOST,
    /**
     * Available on media host media status.
     */
    AVAILABLE_ON_MEDIA_HOST,
    /**
     * Uploading to media host media status.
     */
    UPLOADING_TO_MEDIA_HOST,
    /**
     * Post processing media status.
     */
    POST_PROCESSING,
    /**
     * Post processing failed media status.
     */
    POST_PROCESSING_FAILED,
    /**
     * Error media status.
     */
    ERROR,
    /**
     * Unknown media status.
     */
    UNKNOWN
}
