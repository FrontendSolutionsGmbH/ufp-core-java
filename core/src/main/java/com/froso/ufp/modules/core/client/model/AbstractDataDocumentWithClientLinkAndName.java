package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.documents.*;
import io.swagger.annotations.*;

/**
 * Created by ckleinhuix on 10.12.13.
 */
public class AbstractDataDocumentWithClientLinkAndName
        extends AbstractDataDocumentWithName implements IDataDocumentWithClientLink {


    @ApiModelProperty(position = 1000)
    private DataDocumentLink<IClient> client;


    /**
     * Instantiates a new Abstract data document with client link.
     *
     * @param type the type
     */
    public AbstractDataDocumentWithClientLinkAndName(String type) {

        super(type);
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    @Override
    public DataDocumentLink<IClient> getClient() {
        return client;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    @Override
    public void setClient(DataDocumentLink<IClient> client) {
        this.client = client;
    }
}
