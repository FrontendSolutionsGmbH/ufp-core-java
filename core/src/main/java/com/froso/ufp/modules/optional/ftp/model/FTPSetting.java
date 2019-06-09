package com.froso.ufp.modules.optional.ftp.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 04.01.2016.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("FTP")
        })


)
@Document(collection = FTPSetting.TYPE_NAME)
public class FTPSetting extends AbstractDataDocumentWithName {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "FTPSetting";

    private String host;
    private String port;
    private String userName;
    private String userPassword;
    private String directory;
    private String publicHttp;

    /**
     * Instantiates a new Ftp setting.
     */
    public FTPSetting() {

        super(TYPE_NAME);
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

    /**
     * Gets directory.
     *
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Sets directory.
     *
     * @param directory the directory
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * Gets public http.
     *
     * @return the public http
     */
    public String getPublicHttp() {
        return publicHttp;
    }

    /**
     * Sets public http.
     *
     * @param publicHttp the public http
     */
    public void setPublicHttp(String publicHttp) {
        this.publicHttp = publicHttp + (publicHttp.endsWith("/") ? "" : "/");
    }

    /**
     * Gets public http with path.
     *
     * @return the public http with path
     */
    @ApiModelProperty(readOnly = true)
    public String getPublicHttpWithPath() {
        String result = publicHttp + directory;
        // Clean double slashes
        return result;
    }

    @Override
    public String toString() {
        return "FTPSetting{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", directory='" + directory + '\'' +
                ", publicHttp='" + publicHttp + '\'' +
                '}';
    }
}
