package com.froso.ufp.modules.core.user.model;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by ckleinhuix on 25.05.2015.
 */
public class CounterConsumer extends CounterHelper {


    /**
     * The constant NAME_FAILED_LOGIN.
     */
    public static final String NAME_FAILED_LOGIN = "failedLogin";
    /**
     * The constant NAME_PWRESET.
     */
    public static final String NAME_PWRESET = "passwordResetAttempt";
    /**
     * The constant NAME_UNBLOCKED.
     */
    public static final String NAME_UNBLOCKED = "unblocked";
    /**
     * The constant NAME_EXECUTED.
     */
    public static final String NAME_EXECUTED = "Reseted";
    /**
     * The constant NAME_LOGINS.
     */
    public static final String NAME_LOGINS = "logins";

    /**
     * Instantiates a new Counter consumer.
     */
    public CounterConsumer() {

        super("UserCounters");

    }

    /**
     * Gets failed login.
     *
     * @return the failed login
     */
    @JsonIgnore
    public Integer getFailedLogin() {
        return getCountFor(NAME_FAILED_LOGIN);
    }

    /**
     * Reset failed login.
     */
    public void resetFailedLogin() {

        // INCREASE CALL COUNT FOR RESET
        increaseCountFor(NAME_FAILED_LOGIN + NAME_EXECUTED);
        super.resetCountFor(NAME_FAILED_LOGIN);
    }

    /**
     * Inc failed login.
     */
    public void incFailedLogin() {
        increaseCountFor(NAME_FAILED_LOGIN);
    }

    /**
     * Reset auth_email reset.
     */
    public void resetPasswordReset() {
        increaseCountFor(NAME_PWRESET + NAME_EXECUTED);
        resetCountFor(NAME_PWRESET);
    }

    /**
     * Gets auth_email reset attempt.
     *
     * @return the auth_email reset attempt
     */
    @JsonIgnore
    public Integer getPasswordResetAttempt() {
        return getCountFor(NAME_PWRESET);
    }

    /**
     * Inc auth_email reset attempt.
     */
    public void incPasswordResetAttempt() {
        increaseCountFor(NAME_PWRESET);
    }
}
