package com.froso.ufp.modules.optional.media.model;

import com.froso.ufp.core.domain.documents.*;
import java.util.*;

/**
 * Created by ckleinhuix on 10.12.13.
 */
public abstract class AbstractDataDocumentWithMedia extends AbstractDataDocumentWithName implements DataDocumentWithMedia {

    private List<String> mediaIDs = new ArrayList<>();


    /**
     * Constructor Abstract data document with pictures.
     *
     * @param type the type
     */
    protected AbstractDataDocumentWithMedia(String type) {

        super(type);
    }


    /**
     * Gets media i ds.
     *
     * @return the media i ds
     */
    @Override
    public List<String> getMediaIDs() {

        return mediaIDs;
    }

    /**
     * Sets media i ds.
     *
     * @param mediaIDs the media i ds
     */
    @Override
    public void setMediaIDs(List<String> mediaIDs) {

        this.mediaIDs = mediaIDs;
    }
}
