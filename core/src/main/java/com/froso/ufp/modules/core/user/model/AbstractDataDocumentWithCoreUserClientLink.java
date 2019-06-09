package com.froso.ufp.modules.core.user.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;

/**
 * Created by ckleinhuix on 10.12.13.
 */
public abstract class AbstractDataDocumentWithCoreUserClientLink
        extends ClientReference implements IDataDocumentWithCoreUserLink {


    private DataDocumentLink<ICoreUser> coreUser = new DataDocumentLink<>();


    /**
     * Instantiates a new Abstract data document with client link.
     *
     * @param type the type
     */
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
