package com.froso.ufp.modules.optional.media.model;

import com.froso.ufp.core.domain.documents.*;

/**
 * thjs link component is meant to be used for media links, the end result may contain a media url
 * that resolves to the underlying file
 *
 * @param <T>
 */
public class DataDocumentLinkWithMediaUrl<T> extends DataDocumentLink<T> {

    private static final long serialVersionUID = 9040806419384004355L;
    private String mediaUrl;

    public DataDocumentLinkWithMediaUrl() {

    }

    public DataDocumentLinkWithMediaUrl(String id) {
        super(id);
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
