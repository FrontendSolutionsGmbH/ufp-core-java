package com.froso.ufp.modules.core.roles.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.roles.model.*;
import org.springframework.stereotype.*;

@Service
public class RoleCapabilityService extends AbstractClientRefService<RoleCapability> {


    public RoleCapability getCapabilityAndCreateIfNotPresent(String capabilityName) {

        RoleCapability roleCapability = findOneByKeyValue("name", capabilityName);
        if (roleCapability == null) {

            roleCapability = createNewDefault();
            roleCapability.setName(capabilityName);
            roleCapability = save(roleCapability);
        }

        return roleCapability;
    }

}
