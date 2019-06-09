package com.froso.ufp.modules.optional.sms.model.sms;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import io.swagger.annotations.*;
import org.joda.time.*;

/**
 * Created by ckleinhuix on 28.06.2015.
 */
public class SMSResult {

    @ApiModelProperty(hidden = false, required = true, notes = "Name of the executing service")
    private String serviceName;
    @ApiModelProperty(hidden = false, required = true, notes = "Delivery status")
    private Boolean success = false;
    @ApiModelProperty(hidden = false, required = true, notes = "Any exception occured is catched and stored here")
    private Exception exception;
    @ApiModelProperty(hidden = false, required = true, notes = "The date when sms service has been called")
    private DateTime executionTime;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getExecutionTime() {
        return executionTime;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setExecutionTime(DateTime executionTime) {
        this.executionTime = executionTime;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
