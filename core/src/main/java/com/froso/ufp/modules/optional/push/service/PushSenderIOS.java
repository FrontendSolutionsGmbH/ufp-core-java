package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.model.push.*;
import com.notnoop.apns.*;
import com.notnoop.exceptions.*;
import java.io.*;
import java.util.*;
import org.slf4j.*;

/**
 * Created by alex on 14.03.14.
 */
public class PushSenderIOS implements PushSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushSenderIOS.class);
    private String certDevApplePassword;
    private String certProductionApplePassword;
    private String certDevAppleFilename;
    private String certProductionAppleFilename;

    /**
     * Constructor Push sender iOS.
     *
     * @param certDevApplePassword        the cert dev apple auth_email
     * @param certProductionApplePassword the cert production apple auth_email
     * @param certDevAppleFilename        the cert dev apple filename
     * @param certProductionAppleFilename the cert production apple filename
     */
    public PushSenderIOS(String certDevApplePassword,
                         String certProductionApplePassword,
                         String certDevAppleFilename,
                         String certProductionAppleFilename) {

        this.certDevApplePassword = certDevApplePassword;
        this.certProductionApplePassword = certProductionApplePassword;
        this.certDevAppleFilename = certDevAppleFilename;
        this.certProductionAppleFilename = certProductionAppleFilename;
    }

    /**
     * Send push messages and get inactive device tokens.
     *
     * @param message              the message
     * @param totalInactiveDevices the total inactive devices
     * @param devicesToUpdate      the devices to update
     * @param deviceList           the device list
     * @param useProduction        the use production
     * @return the int
     */
    @Override
    public int sendPushMessagesAndGetInactiveDeviceTokens(AbstractBasePushMessage message,
                                                          List<String> totalInactiveDevices,
                                                          List<AppDevice> devicesToUpdate,
                                                          List<AppDevice> deviceList,
                                                          Boolean useProduction) {

        int successCount = 0;

        PushSendLog sendLog = new PushSendLog();
        ApnsService service = null;
        Set<String> inactiveTokens = new HashSet<String>();
        PayloadBuilder payloadBuilder = buildPayload(message);
        try {
            ApnsiOSDelegate delegate = new ApnsiOSDelegate();
            service = getApplicationDeviceService(useProduction, delegate);
            service.start();
            // You have to delete the devices from you list that no longer
            //have the app installed, see method below
            inactiveTokens = getInactiveDeviceTokens(service);
            // check if the message is too long (it won't be sent if it is)
            //and trim it if it is.
            if (payloadBuilder.isTooLong()) {
                payloadBuilder = payloadBuilder.shrinkBody();
            }
            String payload = payloadBuilder.build();

            sendLog.setPayload(payload);

            successCount += sendTheMessageToDevicesAtOnce(service, payload, deviceList, inactiveTokens, useProduction, delegate);
        } catch (Exception ex) {
            LOGGER.error("Exception" + ex.getMessage(), ex);
            sendLog.setResponse(ex.getMessage());

            // more logging
        } finally {

            // check if the service was successfull initialized and stop it here, if it was
            if (service != null) {
                service.stop();
            }
        }
        message.getSendLog().add(sendLog);
        totalInactiveDevices.addAll(inactiveTokens);
        return successCount;
    }

    private PayloadBuilder buildPayload(AbstractBasePushMessage message) {

        PayloadBuilder payloadBuilder = APNS.newPayload();
        payloadBuilder = payloadBuilder.badge(message.getBadgeValue()).alertBody(message.getMessageTitle()).customFields(message.getParameters())
                .customField(AbstractBasePushMessage.KEY_TYPE, message.getMessageType());
        if (message.getSoundFileName() != null) {
            payloadBuilder.sound(message.getSoundFileName() + message.getSoundFileExtensionForIOS());
        }
        return payloadBuilder;
    }

    private ApnsService getApplicationDeviceService(Boolean useProduction,
                                                    ApnsiOSDelegate delegate) {

        ApnsService service = null;
        if (!useProduction) {
            InputStream certStream = this.getClass().getClassLoader().getResourceAsStream(certDevAppleFilename);
            LOGGER.debug("Input Stream Certificate" + certStream.toString());
            service = APNS.newService().withCert(certStream, certDevApplePassword).withSandboxDestination().withDelegate(delegate).build();
        } else {
            InputStream certStream = this.getClass().getClassLoader().getResourceAsStream(certProductionAppleFilename);
            service = APNS.newService().withCert(certStream, certProductionApplePassword).withProductionDestination().withDelegate(delegate).build();
        }
        return service;
    }

    private Set<String> getInactiveDeviceTokens(ApnsService service) {
        // get the list of the devices that no longer have your app installed from apple
        Map<String, Date> inactiveDevices = service.getInactiveDevices();
        Set<String> tokens = new HashSet<>(inactiveDevices.size());
        for (String token : inactiveDevices.keySet()) {
            tokens.add(token);
        }
        return tokens;
    }

    private int sendTheMessageToDevicesAtOnce(ApnsService service,
                                              String payload,
                                              List<AppDevice> deviceList,
                                              Set<String> inactiveTokens,
                                              Boolean useProduction,
                                              ApnsiOSDelegate delegate) {

        int successCount = 0;
        List<String> tokens = new ArrayList<>();
        // read your simpleUser list
        for (AppDevice appDevice : deviceList) {
            String token = appDevice.getToken();
            if (!inactiveTokens.contains(token)) {
                if (useProduction.equals(appDevice.getIsProduction())) {
                    tokens.add(token);
                } else {
                    LOGGER.debug("PushProblem: A device was not of correct type(Production/Develop)");
                }
            }
        }
        try {
//            int now = (int) (new Date().getTime() / 1000);
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 100); // add 10 days
            date = cal.getTime();

            /*List<EnhancedApnsNotification> notificationList = new ArrayList<EnhancedApnsNotification>();
            for (String token : tokens) {

                EnhancedApnsNotification notification = new EnhancedApnsNotification(EnhancedApnsNotification
                .INCREMENT_ID(), now +60*60,token,payload)

                notificationList.add(notification);
            }*/
            do {
                delegate.getFailedNotifications().clear();
                Collection<? extends EnhancedApnsNotification> notifications = service.push(tokens, payload, date);
                for (ApnsNotification notification : delegate.getFailedNotifications()) {
                    for (int i = tokens.size() - 1; i >= 0; i--) {
                        String failedToken = com.notnoop.apns.internal.Utilities.encodeHex(notification.getDeviceToken());
                        byte[] tokenB = com.notnoop.apns.internal.Utilities.decodeHex(tokens.get(i));
                        String tokenC = com.notnoop.apns.internal.Utilities.encodeHex(tokenB);
                        if (tokenC != null && tokenC.equals(failedToken)) {
                            String realToken = tokens.get(i);
                            tokens.remove(i);
                            inactiveTokens.add(realToken);
                        }
                    }
                }
            }
            while (delegate.getFailedNotifications().size() > 0);
            successCount += tokens.size();
        } catch (Exception ex) {
            // some logging stuff
            LOGGER.error("Exception in sendApplePushsAndGetInactiveDeviceTokens", ex);
            throw ex;
        }
        return successCount;
    }

    /**
     * Test connection.
     *
     * @param s the s
     */
    @Override
    public void testConnection(ServiceStatus s) {

        try {
            ApnsService service = getApplicationDeviceService(false, new ApnsiOSDelegate());
            service.testConnection();

            s.addLogMessage("APNS Sandbox connection tested succesfully");
        } catch (NetworkIOException exception) {
            s.addException(exception);
            s.addLogMessage("APNS Sandbox connection tested with errors");
            s.setIsWorking(false);
        }
        try {
            ApnsService service = getApplicationDeviceService(true, new ApnsiOSDelegate());
            service.testConnection();

            s.addLogMessage("APNS Production connection tested succesfully");
        } catch (NetworkIOException exception) {
            s.addException(exception);
            s.addLogMessage("APNS Production connection tested with errors");
            s.setIsWorking(false);
        }
    }
                                /*
    private int sendTheMessageToDevicesOneByOne(ApnsService service,
                                                String payload,
                                                List<AppDevice> deviceList,
                                                Set<String> inactiveTokens,
                                                Boolean useProduction) {

        int successCount = 0;
        // read your simpleUser list
        for (AppDevice appDevice : deviceList) {
            if (!inactiveTokens.contains(appDevice.getToken())) {
                try {
                    if (useProduction.equals(appDevice.getIsProduction())) {
                        String token = appDevice.getToken();
                        service.push(token, payload);
                        successCount++;
                    } else {
                        LOGGER.debug("PushProblem: Device is not of correct type(Production/Develop)");
                    }
                } catch (Exception ex) {
                    // some logging stuff
                    LOGGER.error("Exception in sendApplePushsAndGetInactiveDeviceTokens", ex);
                }
            }
        }
        return successCount;
    }
    */
}
