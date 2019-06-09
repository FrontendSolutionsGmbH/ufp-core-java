package com.froso.ufp.modules.optional.ftp.controller;

import com.froso.ufp.core.domain.interfaces.*;
import org.apache.commons.net.ftp.*;

/**
 * Created by ckleinhuix on 08.01.2016.
 */
public class MyFTPFile extends FTPFile implements IDataObject {

    private static final long serialVersionUID = -6412137844329926602L;
    private String liveURL;

    public String getLiveURL() {
        return liveURL;
    }

    public void setLiveURL(String liveURL) {
        this.liveURL = liveURL;
    }
}
