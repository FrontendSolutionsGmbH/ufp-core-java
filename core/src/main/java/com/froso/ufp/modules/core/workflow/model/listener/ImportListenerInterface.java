package com.froso.ufp.modules.core.workflow.model.listener;

/**
 * @author alex on 04.02.14.
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@importListenerId")
public interface ImportListenerInterface {
    /**
     * Gets progress.
     *
     * @return the progress
     */
    Double getProgress();

    /**
     * Sets progress in percentage.
     *
     * @param percentage the percentage
     */
    void setProgress(
            Double percentage);

    /**
     * Should continue.
     *
     * @return boolean boolean
     */
    boolean shouldContinue();

    /**
     * Request stop.
     */
    void requestStop();

    /**
     * Report exception.
     *
     * @param ex the ex
     */
    void reportException(Exception ex);

    /**
     * Report info.
     *
     * @param info the info
     */
    void reportInfo(String info);

    /**
     * Report warning.
     *
     * @param warning the warning
     */
    void reportWarning(String warning);

    /**
     * Report error.
     *
     * @param error the error
     */
    void reportError(String error);

}
