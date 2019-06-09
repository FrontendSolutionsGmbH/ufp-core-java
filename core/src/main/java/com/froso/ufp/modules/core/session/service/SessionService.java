package com.froso.ufp.modules.core.session.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.session.interfaces.*;
import com.froso.ufp.modules.core.session.model.*;
import com.froso.ufp.modules.core.user.model.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 10:54 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 * <p>
 * the noteservice provides the crud functionality, and some more, e.g. checking if refered products in a note
 * explicitly exist in the database!
 */
@Service
public class SessionService
        extends AbstractClientRefService<Session> implements ISessionService {

    /**
     * Find by token simple session.
     *
     * @param token the token
     * @return the simple session
     */
    public Session findByToken(String token) {

        return findOneByKeyValue("token", "=" + token);

    }

    /**
     * Deaktivate session by token.
     *
     * @param token the token
     */
    public void deaktivateSessionByToken(String token) {
        setAllSessionsFromUserToInactive(getUserIdForToken(token));

    }

    /**
     * Gets user id for token.
     *
     * @param token the token
     * @return the user id for token
     */
    public String getUserIdForToken(String token) {


        Session session = findByToken(token);
        // if not found return input token ...
        String result = token;

        if (null != session) {
            result = session.getUserLink().getId();
        }


        return result;


    }


    /**
     * Sets all sessions from coreUser to inactive.
     *
     * @param coreUser the coreUser
     */
    public void setAllSessionsFromUserToInactive(ICoreUser coreUser) {
        setAllSessionsFromUserToInactive(coreUser.getId());
    }

    /**
     * Sets all sessions from user to inactive.
     *
     * @param userID the user id
     */
    public void setAllSessionsFromUserToInactive(String userID) {

        Map<String, String> searchMap = new HashMap<>();

        // from repository: @Query("{ userID: ?0 , active: true }")
        searchMap.put("userLink.id", userID);
        searchMap.put("active", "true");

        List<Session> activesessionsfromuser = findByKeyValues(searchMap);

        for (Session session : activesessionsfromuser) {

            //session.setActive(false);
            //session.setCloseTime(DateTime.now());
            //save(session);
            delete(session);

        }
    }

}


