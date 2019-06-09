package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.documents.*;

public class ClientReference extends AbstractDataDocument implements IDataDocumentWithClientLink, IClientReference {


    /**
     * The constant TYPE_NAME.
     */
    private DataDocumentLink<IClient> client = new DataDocumentLink<>();

    /**
     * Constructor Simple client.
     *
     * @param name the name
     */
// Constructor
    public ClientReference(String name) {

        super(name);
    }

    @Override
    public DataDocumentLink<IClient> getClient() {
        return client;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(DataDocumentLink<IClient> client) {
        this.client = client;
    }
}
