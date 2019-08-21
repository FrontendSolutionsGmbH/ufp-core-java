package com.froso.ufp.modules.core.roles.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.roles.model.*;
import org.springframework.stereotype.*;

@Service
public class UserRightService extends AbstractClientRefService<UserRight> {


    public UserRight getCapabilityAndCreateIfNotPresent(String capabilityName) {

        UserRight userRight = findOneByKeyValue("name", capabilityName);
        if (userRight == null) {

            userRight = createNewDefault();
            userRight.setName(capabilityName);
            userRight = save(userRight);
        }

        return userRight;
    }

}
