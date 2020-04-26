package com.froso.ufp.modules.optional.push.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.push.model.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * The type Simple app device service.
 *
 * @author c.Kleinhuis <p>         Provides methods to store Device Information per simpleUser.
 */
@Service
public class AppDeviceService extends AbstractRepositoryService2<AppDevice> {

    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(AppDeviceService.class);

    /**
     * Constructor Simple app device service.
     */
    public AppDeviceService() {

        super(AppDevice.TYPE_NAME);
    }

    /**
     * All appdevices for a given simpleUser id become deleted ;)
     *
     * @param userId the user id
     */
    public void deleteAllFromUser(String userId) {

        List<AppDevice> todelete = getAllAppDevicesForUserId(userId);

        delete(todelete);
    }

    /**
     * returns all registered appdevice tokens for a simpleUser by just an id
     *
     * @param userId the user id
     * @return all app devices for user id
     */
    public List<AppDevice> getAllAppDevicesForUserId(String userId) {

        return findByKeyValue("coreUser._id", userId);
    }

    /**
     * convenience method to find appdevices for a simpleUser by full simpleUser object
     *
     * @param coreUser the simple coreUser
     * @return all app devices for coreUser
     */
    List<AppDevice> getAllAppDevicesForUser(ICoreUser coreUser) {

        return findByKeyValue("coreUser._id", coreUser.getId());
    }

    /**
     * Create or update appdevice by token.
     *
     * @param appDevice the simple app device
     * @return the create or update enum
     */
    public CreateOrUpdateEnum createOrUpdateAppdeviceByToken(AppDevice appDevice) {

        CreateOrUpdateEnum result;
        AppDevice device = findOneByKeyValue("token", appDevice.getToken());
        if (null != device) {
            delete(device);
            LOGGER.debug("Replacing/Updating SimpleAppDevice by token");
            result = CreateOrUpdateEnum.UPDATED;
        } else {
            LOGGER.debug("Creating SimpleAppDevice Entry");
            result = CreateOrUpdateEnum.CREATED;
        }
        save(appDevice);
        return result;
    }

    /**
     * Delete inactive tokens.
     *
     * @param appDeviceTokens the app device tokens
     */
    void deleteInactiveTokens(List<String> appDeviceTokens) {

        for (String deviceToken : appDeviceTokens) {
            deleteAppDeviceWithToken(deviceToken);
        }
    }

    /**
     * deletes a specific appdevice by token
     *
     * @param token the token
     */
    public void deleteAppDeviceWithToken(String token) {

        AppDevice device = findByToken(token);
        if (null != device) {
            delete(device);
        }
    }

    /**
     * Find by token.
     *
     * @param token the token
     * @return the simple app device
     */
    private AppDevice findByToken(String token) {

        return findOneByKeyValue("token", token);
    }
}
