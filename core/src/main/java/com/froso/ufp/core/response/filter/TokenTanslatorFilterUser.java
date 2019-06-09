package com.froso.ufp.core.response.filter;

import com.froso.ufp.modules.core.user.exception.*;
import com.froso.ufp.modules.core.user.model.*;
import javax.servlet.http.*;

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
    protected void doValidateUserRole(String roleString) {

        UserRoleEnum role = UserRoleEnum.valueOf(roleString);
        // grant access if user or admin
        if (!(UserRoleEnum.ROLE_ADMIN.equals(role) || UserRoleEnum.ROLE_USER.equals(role) || UserRoleEnum.ROLE_GUEST.equals(role))) {
            throw new UserTokenException.InvalidRole();
        }
    }

    @Override
    protected void logAccess(HttpServletRequest request) {

        // do not log

    }
}
