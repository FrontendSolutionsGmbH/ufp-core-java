package com.froso.ufp.modules.optional.media.model;

import javax.validation.*;

public class MediaBase64Typed<T> {
    private MediaBase64 media;
    @Valid
    private T data;

    public MediaBase64 getMedia() {
        return media;
    }

    public void setMedia(MediaBase64 media) {
        this.media = media;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
