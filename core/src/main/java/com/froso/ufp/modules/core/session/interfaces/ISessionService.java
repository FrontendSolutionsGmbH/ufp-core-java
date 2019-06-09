package com.froso.ufp.modules.core.session.interfaces;

import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.user.model.*;

/**
 * Created by ckleinhuix on 03.12.2015.
 */
public interface ISessionService {

    /**
     * Find by token simple session.
     *
     * @param token the token
     * @return the simple session
     */
    Session findByToken(String token);

    /**
     * Gets default.
     *
     * @return the default
     */
    Session getDefault();

    /**
     * Save simple session.
     *
     * @param session the session
     * @return the simple session
     */
    Session save(Session session);

    /**
     * Sets all sessions from coreUser to inactive.
     *
     * @param coreUser the coreUser
     */
    void setAllSessionsFromUserToInactive(ICoreUser coreUser);

    /**
     * Sets all sessions from user to inactive.
     *
     * @param userID the user id
     */
    void setAllSessionsFromUserToInactive(String userID);
}
