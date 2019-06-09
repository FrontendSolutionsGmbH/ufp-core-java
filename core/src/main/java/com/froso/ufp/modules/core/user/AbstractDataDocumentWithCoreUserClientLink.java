package com.froso.ufp.modules.core.user;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.user.model.*;

public abstract class AbstractDataDocumentWithCoreUserClientLink
        extends ClientReference implements IDataDocumentWithCoreUserLink {


    private DataDocumentLink<ICoreUser> coreUser = new DataDocumentLink<>();


    public AbstractDataDocumentWithCoreUserClientLink(String type) {

        super(type);
    }

    @Override
    public DataDocumentLink<ICoreUser> getCoreUser() {
        return coreUser;
    }

    @Override
    public void setCoreUser(DataDocumentLink<ICoreUser> coreUser) {
        this.coreUser = coreUser;
    }
}
