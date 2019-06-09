package com.froso.ufp.modules.core.worker.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.workflow.model.status.*;
import io.swagger.annotations.*;
import org.joda.time.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * The type Ufp worker.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("WORKFLOW"),
                @ResourceKeyword("MENU_MONITORING"),
                @ResourceKeyword("MENU_WORKFLOW")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                )
        )

)

@Document(collection = UFPWorker.TYPE_NAME)
public class UFPWorker extends AbstractDataDocumentWithClientLink {
    public static final java.lang.String TYPE_NAME = "UFPWorker";

    @Indexed(unique = true)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;
    private String description;
    private UFPWorkerStatusEnum status = UFPWorkerStatusEnum.IDLE;
    private DateTime lastExecutionStart;
    private Integer intervalSeconds = 1000;
    private DateTime lastExecutionEnd;
    private ServiceStatus serviceStatus;
    private Boolean enabled;
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean instancePresent;

    public UFPWorker() {
        super(TYPE_NAME);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public Long getMillisecondsLastRun() {

        if (lastExecutionEnd != null) {
            return lastExecutionEnd.getMillis() - lastExecutionStart.getMillis();
        }
        return -1L;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInstancePresent() {
        return instancePresent;
    }

    public void setInstancePresent(Boolean instancePresent) {
        this.instancePresent = instancePresent;
    }

    public String getName() {
        return name;
    }

    @ApiModelProperty(readOnly = true,
            notes = "Internal Identifier for this Worker, can not be changed")
    public void setName(String name) {
        this.name = name;
    }

    public UFPWorkerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UFPWorkerStatusEnum status) {
        this.status = status;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getLastExecutionStart() {
        return lastExecutionStart;
    }


    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setLastExecutionStart(DateTime lastExecutionStart) {
        this.lastExecutionStart = lastExecutionStart;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getLastExecutionEnd() {
        return lastExecutionEnd;
    }


    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setLastExecutionEnd(DateTime lastExecutionEnd) {
        this.lastExecutionEnd = lastExecutionEnd;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
