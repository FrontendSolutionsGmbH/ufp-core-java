package com.froso.ufp.modules.core.user.service.util;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.modules.core.user.exception.*;
import com.froso.ufp.modules.core.user.model.*;

/**
 * Created by ckleinhuix on 15.03.14.
 */
public final class UserValidator {

    private UserValidator() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    public static void validateUser(ICoreUser coreUser) {

        if (null != coreUser) {
            validateUserActive(coreUser);
            validateUserBlocked(coreUser);
        } else {
            throw new UserException.UserNotExistant();
        }
    }

    private static void validateUserActive(ICoreUser coreUser) {

        if (!coreUser.getActive()) {
            throw new UserException.UserNotActive();
        }

    }

    private static void validateUserBlocked(ICoreUser coreUser) {
        if (coreUser.getBlocked()) {
            throw new UserException.UserBlocked();
        }
    }
}
