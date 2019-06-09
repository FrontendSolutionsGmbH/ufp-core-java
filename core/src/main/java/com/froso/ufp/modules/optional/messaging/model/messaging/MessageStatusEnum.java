package com.froso.ufp.modules.optional.messaging.model.messaging;

/**
 * The enum Message status enum.
 */
public enum MessageStatusEnum {
    /**
     * Cancelled message status enum.
     */
    CANCELLED,
    /**
     * Error message status enum.
     */
    ERROR,
    /**
     * Error cancelled message status enum.
     */
    ERROR_CANCELLED,
    /**
     * Idle message status enum.
     */
    IDLE,
    /**
     * Processed message status enum.
     */
    PROCESSED,
    /**
     * Waiting to send message status enum.
     */
    WAITING_TO_SEND

}
