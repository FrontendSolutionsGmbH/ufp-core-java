package com.froso.ufp.modules.core.user.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.roles.model.*;
import java.util.*;

/**
 * Created by ckleinhuis on 12.12.2016.
 */
public interface ICoreUser extends IDataDocument {


    Set<DataDocumentLink<UserRole>> getRoles();

    String getFirstName();

    void setFirstName(String name);

    String getLastName();

    void setLastName(String name);

    /**
     * Gets counters.
     *
     * @return the counters
     */
    CounterConsumer getCounters();

    /**
     * Sets counters.
     *
     * @param counters the counters
     */
    void setCounters(CounterConsumer counters);

    /**
     * Gets blocked.
     *
     * @return the blocked
     */
    Boolean getBlocked();

    /**
     * Sets blocked.
     *
     * @param blocked the blocked
     */
    void setBlocked(Boolean blocked);

    /**
     * Gets active.
     *
     * @return the active
     */
    Boolean getActive();

    /**
     * Sets active.
     *
     * @param active the active
     */
    void setActive(Boolean active);
}
