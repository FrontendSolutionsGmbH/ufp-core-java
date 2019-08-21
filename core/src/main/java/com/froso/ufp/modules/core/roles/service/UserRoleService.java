package com.froso.ufp.modules.core.roles.service;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.roles.model.*;

import javax.annotation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserRoleService extends AbstractClientRefService<UserRole> {


    @Autowired
    private UserRightService roleRightService;


    public UserRole getDefaultAdminRole() {
        UserRole role = getOrCreateRole("admin");

        UserRight capability = roleRightService.getCapabilityAndCreateIfNotPresent("admin");

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
            role.getCapabilities().add(new DataDocumentLink<>(capability.getId(), UserRight.TYPE_NAME));
            role = save(role);

        }

        return role;

    }

    public UserRole getOrCreateRole(String roleName) {
        UserRole result = findOneByKeyValue("name", roleName);

        if (result == null) {

            result = createNewDefault();
            result.setName(roleName);
            save(result);

        }
        return result;

    }

    public DataDocumentLink<UserRole> getDefaultAdminRoleLink() {
        return new DataDocumentLink<>(getDefaultAdminRole().getId(), UserRole.TYPE_NAME);
    }

    public UserRole getDefaultUserRole() {
        return getOrCreateRole("user");
    }

    public Set<String> getAllRights(Set<DataDocumentLink<UserRole>> input) {
        Set<String> result = new HashSet<>();
        for (DataDocumentLink<UserRole> roleDefinitionLink : input) {
            UserRole definition = findOne(roleDefinitionLink.getId());
            for (DataDocumentLink<UserRight> capLink : definition.getCapabilities()) {
                UserRight cap = roleRightService.findOne(capLink.getId());
                result.add(cap.getName());
            }
        }
        return result;
    }


    @PostConstruct
    private void createDefaultRolesAndCapabilities() {


        // check for 'user' and 'admin' roles, both shall be created if not present
        UserRole adminRole = getDefaultAdminRole();
        UserRole userRole = getDefaultUserRole();

        UserRight adminCapability = roleRightService.getCapabilityAndCreateIfNotPresent("admin");
        adminRole.getCapabilities().add(new DataDocumentLink<>(adminCapability.getId(), UserRight.TYPE_NAME));
        save(adminRole);

        UserRight userCapability = roleRightService.getCapabilityAndCreateIfNotPresent("user");
        adminRole.getCapabilities().add(new DataDocumentLink<>(userCapability.getId(), UserRight.TYPE_NAME));
        save(userRole);

    }


}
