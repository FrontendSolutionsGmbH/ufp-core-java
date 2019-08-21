package com.froso.ufp.modules.core.roles.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.roles.model.*;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class RoleDefinitionService extends AbstractClientRefService<RoleDefinition> {


    @Autowired
    private RoleCapabilityService roleCapabilityService;


    public RoleDefinition getDefaultAdminRole() {
        RoleDefinition role = getOrCreateRole("admin");

        RoleCapability capability = roleCapabilityService.getCapabilityAndCreateIfNotPresent("admin");

        // verify that capability is assigned to admin role\
        Boolean found = Boolean.FALSE;
        for (DataDocumentLink testCapability : role.getCapabilities()) {
            if (capability.getId().equals(testCapability.getId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            // if admin role not possessing admin capability,add it now
            role.getCapabilities().add(new DataDocumentLink<>(capability.getId(), RoleCapability.TYPE_NAME));
            role = save(role);

        }

        return role;

    }

    public RoleDefinition getOrCreateRole(String roleName) {
        RoleDefinition result = findOneByKeyValue("name", roleName);

        if (result == null) {

            result = createNewDefault();
            result.setName(roleName);
            save(result);

        }
        return result;

    }

    public DataDocumentLink<RoleDefinition> getDefaultAdminRoleLink() {
        return new DataDocumentLink<>(getDefaultAdminRole().getId(), RoleDefinition.TYPE_NAME);
    }

    public RoleDefinition getDefaultUserRole() {
        return getOrCreateRole("user");
    }

    public Set<String> getAllCapabilities(Set<DataDocumentLink<RoleDefinition>> input) {
        Set<String> result = new HashSet<>();
        for (DataDocumentLink<RoleDefinition> roleDefinitionLink : input) {
            RoleDefinition definition = findOne(roleDefinitionLink.getId());
            for (DataDocumentLink<RoleCapability> capLink : definition.getCapabilities()) {
                RoleCapability cap = roleCapabilityService.findOne(capLink.getId());
                result.add(cap.getName());
            }
        }
        return result;
    }


    @PostConstruct
    private void createDefaultRolesAndCapabilities() {


        // check for 'user' and 'admin' roles, both shall be created if not present
        RoleDefinition adminRole = getDefaultAdminRole();
        RoleDefinition userRole = getDefaultUserRole();

        RoleCapability adminCapability = roleCapabilityService.getCapabilityAndCreateIfNotPresent("admin");
        adminRole.getCapabilities().add(new DataDocumentLink<>(adminCapability.getId(), RoleCapability.TYPE_NAME));
        save(adminRole);

        RoleCapability userCapability = roleCapabilityService.getCapabilityAndCreateIfNotPresent("user");
        adminRole.getCapabilities().add(new DataDocumentLink<>(userCapability.getId(), RoleCapability.TYPE_NAME));
        save(userRole);

    }


}
