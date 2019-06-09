package com.froso.ufp.modules.core.authenticate.service;

import com.froso.ufp.modules.core.authenticate.model.*;
import com.froso.ufp.modules.core.client.service.*;
import java.util.*;

abstract public class AbstractCoreAuthenticationsService<T extends Authentication> extends AbstractClientRefService<T> {

    public AbstractCoreAuthenticationsService(String thetype) {
        super();
        this.typeName = thetype;
        this.searchEqualsFields = new HashSet<>();
    }


    public List<Authentication> getAllEntriesForCoreUserId(String coreUserId) {

        return (List<Authentication>) this.findByKeyValue("coreUser.id", coreUserId);

    }
}
