package com.froso.ufp.modules.optional.email.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;

/**
 * Created by ckleinhuix on 04.01.2016.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMUNICATION"),
                @ResourceKeyword("EMAIL"),
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name"),
                        @ResourceVisibleColumn("host"),
                        @ResourceVisibleColumn("port"),
                        @ResourceVisibleColumn("userName"),
                        @ResourceVisibleColumn("emailFrom"),
                        @ResourceVisibleColumn("protocol")
                }))

)
public class EmailServerConfig extends ClientReferenceWithName {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "EmailServerConfig";

    private String host;
    private String port;
    private String userName;
    private String userPassword;
    private String emailFrom;
    private String protocol;

    /**
     * Instantiates a new Ftp setting.
     */
    public EmailServerConfig() {

        super(TYPE_NAME);
    }

    /**
     * Gets email from.
     *
     * @return the email from
     */
    public String getEmailFrom() {
        return emailFrom;
    }

    /**
     * Sets email from.
     *
     * @param emailFrom the email from
     */
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    /**
     * Gets protocol.
     *
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets protocol.
     *
     * @param protocol the protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets host.
     *
     * @param host the host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets port.
     *
     * @param port the port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets user auth_email.
     *
     * @return the user auth_email
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets user auth_email.
     *
     * @param userPassword the user auth_email
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
