package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.core.templatesv1.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.core.workflow.model.status.*;
import com.froso.ufp.modules.optional.messaging.model.messaging.*;
import com.froso.ufp.modules.optional.messaging.service.*;
import com.froso.ufp.modules.optional.push.model.*;
import com.froso.ufp.modules.optional.push.model.push.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * The type Push sender service.
 */
@Service
public class PushSenderService
        implements ServiceStatusReporter, INamedObject {

    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "PushSenderService";
    private static final String PROP_NAME_PUSH_DEV_CERT_APPLE_PASSWORD = "push.dev.certApplePassword";
    private static final String PROP_NAME_PUSH_PROD_CERT_APPLE_PASSWORD = "push.prod.certApplePassword";
    private static final String PROP_NAME_PUSH_DEV_CERT_APPLE_FILENAME = "push.dev.certAppleFilename";
    private static final String PROP_NAME_PUSH_PROD_CERT_APPLE_FILENAME = "push.prod.certAppleFilename";
    private static final String PROP_NAME_PUSH_PROD_ANDROID_API_KEY = "push.prod.androidApiKey";
    private static final String PROP_NAME_PUSH_PROD_ANDROID_PROXY_ACTIVE = "push.prod.androidProxyActive";
    private static final String PROP_NAME_PUSH_PROD_ANDROID_PROXY_HOSTNAME = "push.prod.androidProxyHostname";
    private static final String PROP_NAME_PUSH_PROD_ANDROID_PROXY_PORT = "push.prod.androidProxyPort";
    private static final Logger LOGGER = LoggerFactory.getLogger(PushSenderService.class);
    @Autowired
    private AppDeviceService simpleAppDeviceService;
    @Autowired
    private ICoreUserService entiteitService;
    @Autowired
    private PushService pushService;
    private PushSenderIOS pushSenderIOS;
    private PushSenderAndroid pushSenderAndroid;
    @Autowired
    private PushService simplePushService;
    @Autowired
    private AppDeviceService appDeviceService;
    @Autowired(required = false)
    private TemplateParserService templateParserService;

    @Autowired
    private IPropertyService propertyService;

    /**
     * Send push message to user direct.
     *
     * @param queuePushMessage the message push
     */
    public void sendPushMessageToUserDirect(QueuePushMessage queuePushMessage) {
        ICoreUser coreUser = (ICoreUser) entiteitService.findOne(queuePushMessage.getCoreUser());
        try {
/*            switch (queuePushMessage.getMessageData().getType()) {
                case PushData.TYPE_NAME_ORDER:

                    sendPushMessagesAndGetSuccesfullDeviceCount(coreUser, new OrderStatusUpdatedPushMessage(queuePushMessage.getMessageData().getText()));
                    break;
                case PushData.TYPE_NAME_SUGGESTIOM:

                    sendPushMessagesAndGetSuccesfullDeviceCount(coreUser, new SuggestionReceivedPushMessage(queuePushMessage.getMessageData().getText()));
                    break;
                default:
                    sendPushMessagesAndGetSuccesfullDeviceCount(coreUser, new OrderStatusUpdatedPushMessage(queuePushMessage.getMessageData().getText()));
            }
            */
            sendPushMessagesAndGetSuccesfullDeviceCount(coreUser, queuePushMessage.getData());

            queuePushMessage.setStatus(MessageStatusEnum.PROCESSED);
            simplePushService.save(queuePushMessage);

        } catch (Exception e) {
            LOGGER.error("sendPushMessageToUserDirect", e);

            queuePushMessage.setErrorMessage(e.getMessage());
            queuePushMessage.setRetryCount(queuePushMessage.getRetryCount() + 1);
            queuePushMessage.setStatus(MessageStatusEnum.ERROR);
            if (queuePushMessage.getRetryCount() > propertyService.getPropertyValueInteger(QueueWorkerEmailSender.PROP_NAME_MAX_RETRY_COUNT)) {
                queuePushMessage.setStatus(MessageStatusEnum.ERROR_CANCELLED);

            }
            simplePushService.save(queuePushMessage);

        }
    }

    /**
     * Send push messages and get succesfull device count.
     *
     * @param coreUser  the simple coreUser
     * @param messageDE the message dE
     * @return the int
     */
    public int sendPushMessagesAndGetSuccesfullDeviceCount(ICoreUser coreUser, AbstractBasePushMessage messageDE) {

        LOGGER.info("sendPushMessagesAndGetSuccesfullDeviceCount " + coreUser.getId() + messageDE.getMessageTitle());
        int successCount = 0;
        init();
        List<AppDevice> deviceList = simpleAppDeviceService.getAllAppDevicesForUser(coreUser);
        successCount += sendPushMessageToAppDevicesAndRemoveInactiveDevicesFromRepositoryAndReturnSuccesfullDeviceCount(messageDE, deviceList);
        LOGGER.info("sendPushMessagesAndGetSuccesfullDeviceCount success " + coreUser.getId() + messageDE.getMessageTitle());
        return successCount;
    }

    private void init() {
        // Reinitialise everytime
        pushSenderIOS = new PushSenderIOS(propertyService.getPropertyValue(PROP_NAME_PUSH_DEV_CERT_APPLE_PASSWORD), propertyService.getPropertyValue
                (PROP_NAME_PUSH_PROD_CERT_APPLE_PASSWORD), propertyService.getPropertyValue(PROP_NAME_PUSH_DEV_CERT_APPLE_FILENAME), propertyService.getPropertyValue
                (PROP_NAME_PUSH_PROD_CERT_APPLE_FILENAME));
        pushSenderAndroid = new PushSenderAndroid(propertyService.getPropertyValue(PROP_NAME_PUSH_PROD_ANDROID_API_KEY), propertyService.getPropertyValueBoolean
                (PROP_NAME_PUSH_PROD_ANDROID_PROXY_ACTIVE), propertyService.getPropertyValue(PROP_NAME_PUSH_PROD_ANDROID_PROXY_HOSTNAME), propertyService.getPropertyValueInteger
                (PROP_NAME_PUSH_PROD_ANDROID_PROXY_PORT));
    }

    /**
     * Send push message to app devices and remove inactive devices from repository and return succesfull device count.
     *
     * @param message    the message
     * @param deviceList the device list
     * @return the int
     */
    public int sendPushMessageToAppDevicesAndRemoveInactiveDevicesFromRepositoryAndReturnSuccesfullDeviceCount(AbstractBasePushMessage message, List<AppDevice>
            deviceList) {

        LOGGER.info("Sending PUSH MESSAGE DIREKT " + message.getMessageTitle());
        LOGGER.info("Sending PUSH MESSAGE DIREKT TO " + deviceList);

        int successCount = 0;
        init();
        List<String> totalInactiveDevices = new ArrayList();
        List<AppDevice> devicesToUpdate = new ArrayList();
        List<AppDevice> appleProductionDevices = new ArrayList();
        List<AppDevice> appleDevelopDevices = new ArrayList();
        List<AppDevice> androidDevices = new ArrayList();
        for (AppDevice appDevice : deviceList) {
            if (appDevice.getDeviceType() == AppDeviceTypeEnum.IOS) {
                if (appDevice.getIsProduction()) {
                    appleProductionDevices.add(appDevice);
                } else {
                    appleDevelopDevices.add(appDevice);
                }
            } else if (appDevice.getDeviceType() == AppDeviceTypeEnum.ANDROID) {
                androidDevices.add(appDevice);
            }
        }
        if (!appleProductionDevices.isEmpty()) {
            successCount += pushSenderIOS.sendPushMessagesAndGetInactiveDeviceTokens(message, totalInactiveDevices, devicesToUpdate, appleProductionDevices, true);
        }
        if (!appleDevelopDevices.isEmpty()) {
            successCount += pushSenderIOS.sendPushMessagesAndGetInactiveDeviceTokens(message, totalInactiveDevices, devicesToUpdate, appleDevelopDevices, false);
        }
        if (!androidDevices.isEmpty()) {
            successCount += pushSenderAndroid.sendPushMessagesAndGetInactiveDeviceTokens(message, totalInactiveDevices, devicesToUpdate, androidDevices, true);
        }
        simpleAppDeviceService.deleteInactiveTokens(totalInactiveDevices);
        for (AppDevice device : devicesToUpdate) {
            simpleAppDeviceService.createOrUpdateAppdeviceByToken(device);
        }
        LOGGER.info("Sending PUSH MESSAGE DIREKT SUCCESS " + successCount);

        return successCount;
    }

    /**
     * Send push message to user.
     *
     * @param userID   the user id
     * @param template the template
     * @param data     the data
     * @param type     the type
     */
    public void sendPushMessageToUser(String userID, String template, Map<String, Object> data, String type) {
        ICoreUser coreUser = (ICoreUser) entiteitService.findOne(userID);

        // just create a message entry to processed by the queue worker
        QueuePushMessage queuePushMessage = pushService.createNewDefault();
        queuePushMessage.getMessageData().setType(type);
        queuePushMessage.getCoreUser().setId(userID);
        queuePushMessage.getMessageData().setText(templateParserService.parseTemplate(template, data));
        queuePushMessage.getMessageData().setAppDevices(simpleAppDeviceService.getAllAppDevicesForUser(coreUser));

        List<AppDevice> deviceListForUser = appDeviceService.getAllAppDevicesForUserId(userID);
        queuePushMessage.getMessageData().getAppDevices().addAll(deviceListForUser);


        VelocityMessage velocityMessage = new VelocityMessage();
        velocityMessage.setVelocityTemplatePath(template);
        velocityMessage.setData(data);
        queuePushMessage.setVelocityMessage(velocityMessage);
        pushService.save(queuePushMessage);

    }

    public String getName() {

        return NAME;

    }

    /**
     * Gets service status.
     *
     * @return the service status
     */
    @Override
    public ServiceStatus getServiceStatus() {

        init();
        ServiceStatus s = new ServiceStatus();
        s.setName(NAME);
        s.addLogMessage("Testing iOS Sandbox and Production Connection");
        pushSenderIOS.testConnection(s);
        pushSenderAndroid.testConnection(s);
        return s;
    }
}
