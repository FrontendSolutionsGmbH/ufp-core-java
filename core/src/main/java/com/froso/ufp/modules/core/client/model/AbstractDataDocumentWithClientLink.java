package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.documents.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 10.12.13.
 */
public class AbstractDataDocumentWithClientLink
        extends AbstractDataDocument implements IDataDocumentWithClientLink {

    @ApiModelProperty(position = 1000, notes = "The linked Client")
    private DataDocumentLink<IClient> client;

    /**
     * Instantiates a new Abstract data document with client link.
     *
     * @param type the type
     */
    public AbstractDataDocumentWithClientLink(String type) {

        super(type);
    }

    /**
     * Gets client.
     *
     * @return the client
     */
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
