package com.froso.ufp.core.response.filter;

import com.froso.ufp.core.*;
import com.froso.ufp.modules.core.user.exception.*;
import com.froso.ufp.modules.core.user.model.*;
import javax.servlet.http.*;

/**
 * Created with IntelliJ IDEA.
 * Entiteit: ck
 * Date: 19.03.14
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class TokenTranslatorFilterAdmin extends AbstractTokenTranslatorFilter {


    /**
     * Gets main ressource.
     *
     * @return the main ressource
     */
    @Override
    protected String getMainRessource() {

        return UFPConstants.ADMIN;
    }

    /**
     * Validate user role.
     *
     * @param roleString the role string
     */
    @Override
    protected void doValidateUserRole(String roleString) {

        UserRoleEnum role = UserRoleEnum.valueOf(roleString);
        if (!UserRoleEnum.ROLE_ADMIN.equals(role)) {
            throw new UserTokenException.InvalidRole();
        }
    }

    @Override
    protected void logAccess(HttpServletRequest request) {

        //

    }
}
