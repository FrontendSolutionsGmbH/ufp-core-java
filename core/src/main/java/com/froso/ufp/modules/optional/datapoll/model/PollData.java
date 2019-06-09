package com.froso.ufp.modules.optional.datapoll.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */


@UFPResourceMetadataAnnotation(
        keywords = @ResourceKeywords({
                @ResourceKeyword("KRYPTOSCAN"),
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name")
                }
                )
        )
)


public class PollData extends AbstractDataDocumentWithClientLink {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "PollData";

    private String type;
    private String request;
    private String data;
    private DataDocumentLink<PollDataConfig> initiator;

    /**
     * Constructor Simple app device.
     */
// Constructor
    public PollData() {


        super(TYPE_NAME);
    }

    public DataDocumentLink<PollDataConfig> getInitiator() {
        return initiator;
    }

    public void setInitiator(DataDocumentLink<PollDataConfig> initiator) {
        this.initiator = initiator;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }


}
