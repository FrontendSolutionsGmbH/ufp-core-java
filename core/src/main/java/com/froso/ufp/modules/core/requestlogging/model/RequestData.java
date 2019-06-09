package com.froso.ufp.modules.core.requestlogging.model;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import java.util.*;
import javax.servlet.http.*;

/**
 * Created by ckleinhuix on 02/09/2016.
 */
public class RequestData {

    private String body;
    private String path;
    private String method;
    private String query;
    private String servletPath;
    private String remoteAddr;
    private String contextPath;
    private String pathTranslated;

    private List<KeyValueItem> headers = new ArrayList<>();
    private String contentType;
    private String characterEncoding;
    private String pathInfo;
    private String requestedURI;
    private String authType;

    private List<KeyValueItem> queryParameters = new ArrayList<>();

    //private Map<String, String> queryParams;
    //private Map<String, String[]> parameterMap;
    private Cookie[] cookies;

    /**
     * Gets requested uri.
     *
     * @return the requested uri
     */
    public String getRequestedURI() {
        return requestedURI;
    }

    /**
     * Sets requested uri.
     *
     * @param requestedURI the requested uri
     */
    public void setRequestedURI(String requestedURI) {
        this.requestedURI = requestedURI;

    }

    /**
     * Gets servlet path.
     *
     * @return the servlet path
     */
    public String getServletPath() {
        return servletPath;
    }

    /**
     * Sets servlet path.
     *
     * @param servletPath the servlet path
     */
    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    /**
     * Gets auth type.
     *
     * @return the auth type
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * Sets auth type.
     *
     * @param authType the auth type
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * Get cookies cookie [ ].
     *
     * @return the cookie [ ]
     */
    public Cookie[] getCookies() {
        return cookies;
    }

    /**
     * Sets cookies.
     *
     * @param cookies the cookies
     */
    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    /**
     * Gets parameter map.
     *
     * @return the parameter map
     */
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
     * Gets context path.
     *
     * @return the context path
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * Sets context path.
     *
     * @param contextPath the context path
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * Gets path translated.
     *
     * @return the path translated
     */
    public String getPathTranslated() {
        return pathTranslated;
    }

    /**
     * Sets path translated.
     *
     * @param pathTranslated the path translated
     */
    public void setPathTranslated(String pathTranslated) {
        this.pathTranslated = pathTranslated;
    }

    /**
     * Gets path info.
     *
     * @return the path info
     */
    public String getPathInfo() {
        return pathInfo;
    }

    /**
     * Sets path info.
     *
     * @param pathInfo the path info
     */
    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    /**
     * Gets method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets method.
     *
     * @param method the method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets http status.
     *
     * @return the http status
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
        this.body = body;
    }

    public List<KeyValueItem> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(List<KeyValueItem> queryParameters) {
        this.queryParameters = queryParameters;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets query.
     *
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets query.
     *
     * @param query the query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets source addr.
     *
     * @return the source addr
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * Sets source addr.
     *
     * @param remoteAddr the source addr
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

}
