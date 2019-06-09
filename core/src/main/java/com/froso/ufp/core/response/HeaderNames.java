package com.froso.ufp.core.response;

/**
 * Created by ckleinhuix on 30.05.2015.
 */
public class HeaderNames {

    /**
     * The constant HEADER_X_RETAIL_HMAC.
     */
// response header, containing the hmac value for the server response
    public static final String HEADER_X_RETAIL_HMAC = "x-ufp-hmac";
    /**
     * The constant HEADER_X_RETAIL_TIMESTAMP.
     */
// response header, containing the hmac value for the server response
    public static final String HEADER_X_RETAIL_TIMESTAMP = "x-ufp-timestamp";


    /**
     * The constant HEADER_X_UFP_CLIENT.
     */
// client app version (web,android,ios) is submitted to the server through this header
    public static final String HEADER_X_UFP_CLIENT = "x-ufp-client";

    /**
     * The constant HEADER_X_UFP_VERSION.
     */
// required server api version is submitted through this header to the server
    public static final String HEADER_X_UFP_VERSION = "x-ufp-version";

    /**
     * The constant HEADER_X_UFP_REQUEST_INDEX.
     */
// this is a security request counter, that modifies the secret to create the hmac key ... :/
    public static final String HEADER_X_UFP_REQUEST_INDEX = "x-ufp-reqindex";
    /**
     * The constant HEADER_X_UFP_TOKEN.
     */
// user session token... :/
    public static final String HEADER_X_UFP_TOKEN = "x-ufp-token";
    /**
     * The constant HEADER_X_UFP_REQUEST.
     */
    public static final String HEADER_X_UFP_REQUESTLOG = "x-ufp-requestlog";
    /**
     * The constant HEADER_EXPOSE_HEADERS.
     */
    public static final String HEADER_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    /**
     * The constant HEADER_STRICT_TRANSPORT.
     */
    public static final String HEADER_STRICT_TRANSPORT = "Strict-Transport-Security";

}
