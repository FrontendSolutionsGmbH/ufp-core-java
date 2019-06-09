package com.froso.ufp.modules.core.requestlogging.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Document(collection = RequestLogging.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_MONITORING"),
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("MENU_REQUESTLOGGING"),
                @ResourceKeyword("REQUESTLOGGING")
        })


)
public class RequestLogging extends AbstractDataDocumentWithClientLink {


    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "RequestLogging";
    private Boolean autoDelete = true;
    private RequestData requestData = new RequestData();
    private ResponseData responseData = new ResponseData();
    private Long durationMilliSeconds = 0L;

    /**
     * Constructor Simple app device.
     */
// Constructor
    public RequestLogging() {

        super(TYPE_NAME);
    }

    /**
     * Gets duration milli seconds.
     *
     * @return the duration milli seconds
     */
    public Long getDurationMilliSeconds() {
        return durationMilliSeconds;
    }

    /**
     * Sets duration milli seconds.
     *
     * @param durationMilliSeconds the duration milli seconds
     */
    public void setDurationMilliSeconds(Long durationMilliSeconds) {
        this.durationMilliSeconds = durationMilliSeconds;
    }

    /**
     * Gets auto delete.
     *
     * @return the auto delete
     */
    public Boolean getAutoDelete() {
        return autoDelete;
    }

    /**
     * Sets auto delete.
     *
     * @param autoDelete the auto delete
     */
    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    /**
     * Gets request data.
     *
     * @return the request data
     */
    public RequestData getRequestData() {
        return requestData;
    }

    /**
     * Sets request data.
     *
     * @param requestData the request data
     */
    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    /**
     * Gets response data.
     *
     * @return the response data
     */
    public ResponseData getResponseData() {
        return responseData;
    }

    /**
     * Sets response data.
     *
     * @param responseData the response data
     */
    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

}
