package com.froso.ufp.modules.core.user.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;

public abstract class AbstractDataDocumentWithCoreUserLink
        extends ClientReference implements IDataDocumentWithCoreUserLink {

    private DataDocumentLink<ICoreUser> coreUser = new DataDocumentLink<>();

    /**
     * Instantiates a new Abstract data document with client link.
     *
     * @param type the type
     */
    public AbstractDataDocumentWithCoreUserLink(String type) {

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
