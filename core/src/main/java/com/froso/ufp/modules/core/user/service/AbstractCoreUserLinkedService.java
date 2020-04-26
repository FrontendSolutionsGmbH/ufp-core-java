package com.froso.ufp.modules.core.user.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.user.model.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 11:03 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 * <p>
 * the userservice handles application users, and gives a method for checking a login of a simpleUser
 */

abstract public class AbstractCoreUserLinkedService<T extends IDataDocumentWithCoreUserLink> extends AbstractRepositoryService2<T> {

    protected AbstractCoreUserLinkedService(String thetype) {
        super(thetype);
        typeName = thetype;
        searchEqualsFields = new HashSet<>();
    }

    public List<T> getAllEntriesForCoreUserId(String coreUserId) {

        return findByKeyValue("coreUser.id", coreUserId);

    }
}
