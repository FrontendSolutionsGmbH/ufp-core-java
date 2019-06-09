package com.froso.ufp.modules.optional.datapoll.model;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */


@UFPResourceMetadataAnnotation(
        keywords = @ResourceKeywords({
                @ResourceKeyword("POLLDATA"),
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


public class PollDataConfig extends AbstractDataDocumentWithClientLinkAndName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "PollDataConfig";

    private Boolean enabled;
    private String url;
    private Integer intervalMinutes;
    private DateTime lastExecuted;
    private String lastResult;
    private DateTime start;
    private DateTime end;

    public PollDataConfig() {

        super(TYPE_NAME);
    }

    public Integer getIntervalMinutes() {
        return intervalMinutes;
    }

    public void setIntervalMinutes(Integer intervalMinutes) {
        this.intervalMinutes = intervalMinutes;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getLastExecuted() {
        return lastExecuted;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setLastExecuted(DateTime lastExecuted) {
        this.lastExecuted = lastExecuted;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getStart() {
        return start;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)

    public void setStart(DateTime start) {
        this.start = start;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)

    public DateTime getEnd() {
        return end;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setEnd(DateTime end) {
        this.end = end;
    }

    /**
     * Constructor Simple app device.
     */
// Constructor
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
