package com.froso.ufp.modules.optional.securitylog.model;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import java.util.*;
import org.joda.time.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 30.05.2015.
 */
@Document(collection = SecurityLog.TYPE_NAME)
public class SecurityLog extends AbstractDataDocument {

    /**
     * The constant LOG_TYPE_ADMIN_WRITE.
     */
    public static final String LOG_TYPE_ADMIN_WRITE = "Admin Write Access";
    /**
     * The constant LOG_LOGIN_FAIL.
     */
    public static final String LOG_LOGIN_FAIL = "Login Fail";

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "SecurityLog";
    @Indexed
    private String userID;
    private DateTime creationTime = DateTime.now();
    private DateTime closeTime;
    private String remoteHost;
    private List<RequestLog> requestLog = new ArrayList<>();
    private String logType;
    private Map<String, String[]> requestParameters;

    /**
     * Instantiates a new Simple security log.
     */
    public SecurityLog() {

        super(TYPE_NAME);
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public static String getTypeName() {
        return TYPE_NAME;
    }

    /**
     * Gets request parameters.
     *
     * @return the request parameters
     */
    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    /**
     * Sets request parameters.
     *
     * @param requestParameters the request parameters
     */
    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    /**
     * Gets log type.
     *
     * @return the log type
     */
    public String getLogType() {
        return logType;
    }

    /**
     * Sets log type.
     *
     * @param logType the log type
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * Gets request log.
     *
     * @return the request log
     */
    public List<RequestLog> getRequestLog() {
        return requestLog;
    }

    /**
     * Sets request log.
     *
     * @param requestLog the request log
     */
    public void setRequestLog(List<RequestLog> requestLog) {
        this.requestLog = requestLog;
    }


    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Gets creation time.
     *
     * @return the creation time
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets creation time.
     *
     * @param creationTime the creation time
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Gets close time.
     *
     * @return the close time
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCloseTime() {
        return closeTime;
    }

    /**
     * Sets close time.
     *
     * @param closeTime the close time
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCloseTime(DateTime closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * Gets remote host.
     *
     * @return the remote host
     */
    public String getRemoteHost() {
        return remoteHost;
    }

    /**
     * Sets remote host.
     *
     * @param remoteHost the remote host
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
}
