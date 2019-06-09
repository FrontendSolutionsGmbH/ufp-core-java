package com.froso.ufp.modules.optional.push.service;

import com.notnoop.apns.*;
import java.util.*;
import org.slf4j.*;

/**
 * Created by alex on 15.08.14.
 */
public class ApnsiOSDelegate implements ApnsDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushSenderIOS.class);

    private List<ApnsNotification> failedNotifications = new ArrayList<>();

    /**
     * Gets failed notifications.
     *
     * @return the failed notifications
     */
    public List<ApnsNotification> getFailedNotifications() {

        return failedNotifications;
    }

    /**
     * Message sent.
     *
     * @param message the message
     * @param resent  the resent
     */
    @Override
    public void messageSent(ApnsNotification message,
                            boolean resent) {

    }

    /**
     * Message send failed.
     *
     * @param message the message
     * @param e       the e
     */
    @Override
    public void messageSendFailed(ApnsNotification message,
                                  Throwable e) {

        failedNotifications.add(message);
    }

    /**
     * Connection closed.
     *
     * @param e                 the e
     * @param messageIdentifier the message identifier
     */
    @Override
    public void connectionClosed(DeliveryError e,
                                 int messageIdentifier) {

        LOGGER.debug("ApnsiOSDelegate" + e.toString() + " " + messageIdentifier);
    }

    /**
     * Cache length exceeded.
     *
     * @param newCacheLength the new cache length
     */
    @Override
    public void cacheLengthExceeded(int newCacheLength) {

    }

    /**
     * Notifications resent.
     *
     * @param resendCount the resend count
     */
    @Override
    public void notificationsResent(int resendCount) {

    }
}
