package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.model.push.*;
import com.google.android.gcm.server.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;

/**
 * Created by alex on 14.03.14.
 */
public class PushSenderAndroid implements PushSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushSenderAndroid.class);
    private String apiKey;
    private Boolean proxyActive;
    private String proxyHostname;
    private Integer proxyPort;

    /**
     * Constructor Push sender android.
     *
     * @param apiKey        the api key
     * @param proxyActive   the proxy active
     * @param proxyHostname the proxy hostname
     * @param proxyPort     the proxy port
     */
    public PushSenderAndroid(String apiKey, Boolean proxyActive, String proxyHostname, Integer proxyPort) {

        this.apiKey = apiKey;
        this.proxyActive = proxyActive;
        this.proxyHostname = proxyHostname;
        this.proxyPort = proxyPort;
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
    public int sendPushMessagesAndGetInactiveDeviceTokens(AbstractBasePushMessage message, List<String> totalInactiveDevices, List<AppDevice> devicesToUpdate, List<AppDevice> deviceList, Boolean useProduction) {

        return sendPushMessagesAndGetInactiveDeviceTokensAtOnce(message, totalInactiveDevices, devicesToUpdate, deviceList, useProduction);
    }

    private int sendPushMessagesAndGetInactiveDeviceTokensAtOnce(AbstractBasePushMessage message, List<String> totalInactiveDevices, List<AppDevice> devicesToUpdate, List<AppDevice> deviceList, Boolean useProduction) {

        int successCount = 0;
        Sender sender = createSender();
        List<String> inactiveDeviceTokens = new ArrayList<>();
        List<String> registrationIds = new ArrayList<>();
        for (AppDevice d : deviceList) {
            String registrationId = d.getToken();
            if (!registrationIds.contains(registrationId)) {
                registrationIds.add(registrationId);
            } else {
                totalInactiveDevices.add(registrationId);
            }
        }
        Message m = buildMessage(message);
        try {
            MulticastResult multiResult = sender.sendNoRetry(m, registrationIds);
            for (int i = 0; i < multiResult.getResults().size(); i++) {
                Result result = multiResult.getResults().get(i);
                if (result.getErrorCodeName() != null) {
                    if (Constants.ERROR_NOT_REGISTERED.equals(result.getErrorCodeName())) {
                        // application has been removed from device - unregister database or remove from database
                        inactiveDeviceTokens.add(deviceList.get(i).getToken());
                    } else {
                        LOGGER.error("Unkown Android-Push-Error " + result.getErrorCodeName());
                    }
                }
                if (result.getCanonicalRegistrationId() != null) {
                    // new id
                    AppDevice deviceToUpdate = deviceList.get(i);
                    boolean foundDuplicate = false;
                    for (int j = devicesToUpdate.size() - 1; j >= 0; j--) {
                        AppDevice device2 = devicesToUpdate.get(j);
                        if (result.getCanonicalRegistrationId().equals(device2.getToken())) {
                            foundDuplicate = true;
                            break;
                        }
                    }
                    if (!foundDuplicate) {
                        deviceToUpdate.setToken(result.getCanonicalRegistrationId());
                        devicesToUpdate.add(deviceToUpdate);
                    } else {
                        inactiveDeviceTokens.add(deviceList.get(i).getToken());
                    }
                }
            }
            successCount += multiResult.getSuccess();
        } catch (IOException e) {
            LOGGER.error("Push Android error", e);
        }
        totalInactiveDevices.addAll(inactiveDeviceTokens);
        return successCount;
    }

    private Sender createSender() {

        Sender s = null;
        if (!proxyActive) {
            s = new Sender(apiKey, Endpoint.FCM);
        } else {
            s = new AndroidProxySender(apiKey, proxyHostname, proxyPort);
        }
        return s;
    }

    private Message buildMessage(AbstractBasePushMessage message) {

        return new Message.Builder().setData(message.getParameters()).collapseKey(message.getMessageType()).addData("title", message.getMessageTitle()).addData("sound", message.getSoundFileName() + message.getSoundFileExtensionForAndroid()).addData(AbstractBasePushMessage.KEY_TYPE, message.getMessageType()).build();
    }

    /**
     * Test connection.
     *
     * @param status the status
     */
    @Override
    public void testConnection(ServiceStatus status) {

        try {
            OrderStatusUpdatedPushMessage message = new OrderStatusUpdatedPushMessage("Test Push Message");
            message.setOrderId("1234");
            message.setUserId("1234");
            message.setBadgeValue(1);
            // message.setMessageTitle("test");
            String registrationId = "1234567";
            Message m = buildMessage(message);
            try {
                Sender sender = createSender();
                Result result = sender.sendNoRetry(m, registrationId);
                if (result.getErrorCodeName() != null) {
                    if (Constants.ERROR_UNAVAILABLE.equals(result.getErrorCodeName())) {
                        status.addLogMessage("Android - random message to unknown device -- ERROR - Unreachable Server " + result.getErrorCodeName());
                        status.setIsWorking(false);
                    } else {
                        status.addLogMessage("Sended random message to unknown device -- SUCCESS - Android-Push-Result  " + result.getErrorCodeName());
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Push Sender Android error", e);
                status.addLogMessage("Android - random message to unknown device -- IOException");
                status.setIsWorking(false);
            }
        } catch (Exception e) {
            status.addLogMessage("Android - random message to unknown device -- Unknown ERROR - " + e.getMessage());
            status.addException(e);
            status.setIsWorking(false);
        }
    }

    /*
        private SimpleAppDevice findDeviceByToken(List<SimpleAppDevice> devices, String token) {
            for (SimpleAppDevice device : devices) {
                if (device.getToken().equals(token)) {
                    return device;
                }
            }
            return null;
        }
    */
    /*
    private int sendPushMessagesAndGetInactiveDeviceTokensDeviceByDevice(AbstractBasePushMessage message, List<String> totalInactiveDevices, List<AppDevice> deviceList, Boolean useProduction) {

        int successCount = 0;
        Sender sender = createSender();
        List<String> inactiveDeviceTokens = new ArrayList<>();
        for (AppDevice d : deviceList) {
            String registration_id = d.getToken();
            Message m = buildMessage(message);
            try {
                Result result = sender.sendNoRetry(m, registration_id);
                if (result.getErrorCodeName() != null) {
                    if (Constants.ERROR_NOT_REGISTERED.equals(result.getErrorCodeName())) {
                        // application has been removed from device - unregister database or remove from database
                        inactiveDeviceTokens.add(d.getToken());
                    } else {
                        LOGGER.error("Unkown Android-Push-Error " + result.getErrorCodeName());
                    }
                }
                successCount += (result.getMessageId() != null) ? 1 : 0;
            } catch (IOException e) {
                LOGGER.error("Push Sender Android error",e);
            }
        }
        totalInactiveDevices.addAll(inactiveDeviceTokens);
        return successCount;
    }   */
}
