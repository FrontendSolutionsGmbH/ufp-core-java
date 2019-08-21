package com.froso.ufp.core.response.filter;

import com.froso.ufp.modules.core.roles.model.*;
import com.froso.ufp.modules.core.user.exception.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * CoreUser: ck
 * Date: 19.03.14
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class TokenTanslatorFilterUser extends AbstractTokenTranslatorFilter {
    private String mainResource = "User";


    /**
     * Instantiates a new Token tanslator filter user.
     *
     * @param resourceName the resource name
     */
    public TokenTanslatorFilterUser(String resourceName) {
        this.mainResource = resourceName;
    }

    /**
     * Instantiates a new Token tanslator filter user.
     */
    public TokenTanslatorFilterUser() {

    }

    /**
     * Gets main ressource.
     *
     * @return the main ressource
     */
    @Override
    protected String getMainRessource() {

        return mainResource;
    }

    /**
     * Validate user role.
     *
     * @param roleString the role string
     */
    @Override
    protected void doValidateUserRole(Set<String> capabilities) {

        for(String capability:capabilities){
            if (RoleRightsDefaultEnum.admin.equals(capability)) {
                return;
            }
            if (RoleRightsDefaultEnum.user.equals(capability)) {
                return;
            }

        }
        throw new UserTokenException.InvalidRole();
    }

    @Override
    protected void logAccess(HttpServletRequest request) {

        // do not log

    }
}
