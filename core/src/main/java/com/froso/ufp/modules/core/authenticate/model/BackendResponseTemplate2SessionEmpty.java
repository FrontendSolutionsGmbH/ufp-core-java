package com.froso.ufp.modules.core.authenticate.model;

import com.froso.ufp.core.response.*;
import java.io.*;

/**
 * The type Backend response template 2 session empty.
 */
public class BackendResponseTemplate2SessionEmpty implements Serializable {

    private static final long serialVersionUID = -8468747628112213658L;
    private SessionResponseStatusTyped  info = new SessionResponseStatusTyped();

    /**
     * Gets info.
     *
     * @return the info
     */
    public SessionResponseStatusTyped getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(SessionResponseStatusTyped info) {
        this.info = info;
    }
}
