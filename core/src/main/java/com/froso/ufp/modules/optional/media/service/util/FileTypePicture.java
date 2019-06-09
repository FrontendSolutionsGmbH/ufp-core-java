package com.froso.ufp.modules.optional.media.service.util;

import com.froso.ufp.modules.optional.media.model.media.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 27.03.2015.
 */
public class FileTypePicture implements FileTypeDeterminer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypePicture.class);

    /**
     * Update media element.
     *
     * @param mediaElement the media element
     * @param bytes        the bytes
     * @return the boolean
     */
    @Override
    public boolean isOfTypeAndSet(MediaElement mediaElement, byte[] bytes) {

        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            // Check if from inputstream can an imageio instance created
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            if (bufferedImage != null) {
                mediaElement.setMediaFormat(MediaFormat.PICTURE);
                mediaElement.setProperty("WIDTH", String.valueOf(bufferedImage.getWidth()));
                mediaElement.setProperty("HEIGHT", String.valueOf(bufferedImage.getHeight()));
                mediaElement.setMediaStatus(MediaStatus.PROCESSED);
                mediaElement.setSize((long) bytes.length);

                byteArrayInputStream.close();
                return true;
            }
            byteArrayInputStream.close();
        } catch (IOException e) {
            LOGGER.info("NOT AN IMAGE", e);


        }
        return false;
    }
}
