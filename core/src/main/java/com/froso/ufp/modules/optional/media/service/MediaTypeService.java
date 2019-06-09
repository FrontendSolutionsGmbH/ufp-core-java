package com.froso.ufp.modules.optional.media.service;

import com.froso.ufp.modules.optional.media.model.media.*;
import com.froso.ufp.modules.optional.media.service.util.*;
import java.io.*;
import java.util.*;
import org.apache.commons.io.*;
import org.slf4j.*;
import org.springframework.integration.file.remote.session.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 12.12.2015.
 */
@Service
public class MediaTypeService {


    private static final Logger LOGGER = LoggerFactory.getLogger(MediaTypeService.class);
    private List<FileTypeDeterminer> fileTypeDeterminers = new ArrayList<>();

    /**
     * Update media element.
     *
     * @param mediaElement the media element
     * @param session      the session
     */
    public void updateMediaElement(MediaElement mediaElement, Session session) {

        try {
            InputStream inputStream = session.readRaw(mediaElement.getMediaHostFileName());
            byte[] bytes = IOUtils.toByteArray(inputStream);

            inputStream.close();
            session.finalizeRaw();

            for (FileTypeDeterminer determiner : fileTypeDeterminers) {
                if (determiner.isOfTypeAndSet(mediaElement, bytes)) {
                    // succesfully processed
                    return;
                }
            }


        } catch (IOException e) {
            LOGGER.error("updateMediaElement", e);
        }

        mediaElement.setMediaFormat(MediaFormat.UNKNOWN);
        mediaElement.setMediaStatus(MediaStatus.UNKNOWN);


    }

    /**
     * Register media type determiner.
     *
     * @param determiner the determiner
     */
    public void registerMediaTypeDeterminer(FileTypeDeterminer determiner) {
        fileTypeDeterminers.add(determiner);
    }
}
