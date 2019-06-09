package com.froso.ufp.modules.core.requestlogging.model;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import java.util.*;

/**
 * Created by ckleinhuix on 02/09/2016.
 */
public class ResponseData {
    private List<KeyValueItem> headers;
    private String body;
    private String contentType;
    private String characterEncoding;
    private Integer httpStatusCode;

    /**
     * Gets character encoding.
     *
     * @return the character encoding
     */
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    /**
     * Sets character encoding.
     *
     * @param characterEncoding the character encoding
     */
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    /**
     * Gets content type.
     *
     * @return the content type
     */
    public String getContentType() {

        return contentType;
    }

    /**
     * Sets content type.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**

     * Gets http status.
     *
     * @return the http status
     */

    /**
     * Gets http status code.
     *
     * @return the http status code
     */
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Sets http status code.
     *
     * @param httpStatusCode the http status code
     */
    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Gets headers.
     *
     * @return the headers
     */
    public List<KeyValueItem> getHeaders() {
        return headers;
    }

    /**
     * Sets headers.
     *
     * @param headers the headers
     */
    public void setHeaders(List<KeyValueItem> headers) {
        this.headers = headers;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body) {
        if (body.length() > 8096) {
            this.body = body.substring(0, 8096) + "...snipped...";
        } else {
            this.body = body;
        }
    }
}
