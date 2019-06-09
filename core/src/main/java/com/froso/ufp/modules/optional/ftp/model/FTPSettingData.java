package com.froso.ufp.modules.optional.ftp.model;

import io.swagger.annotations.*;

public class FTPSettingData {
    private String host;
    private String port;
    private String userName;
    private String userPassword;
    private String directory;
    private String publicHttp;

    @ApiModelProperty(readOnly = true)
    public String getPublicHttpWithPath() {
        String result = publicHttp + directory;
        // Clean double slashes
        return result;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getPublicHttp() {
        return publicHttp;
    }

    public void setPublicHttp(String publicHttp) {
        this.publicHttp = publicHttp;
    }
}
