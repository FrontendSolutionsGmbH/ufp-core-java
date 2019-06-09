package com.froso.ufp.modules.optional.media.service;

import com.froso.ufp.modules.optional.media.model.*;
import com.froso.ufp.modules.optional.media.model.media.*;
import com.froso.ufp.modules.optional.media.service.util.*;
import org.imgscalr.*;
import org.slf4j.*;
import org.springframework.integration.file.remote.session.*;
import org.springframework.stereotype.*;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

@Service
public class PictureService extends AbstractFileServiceLocalFilesSystem<FilePicture> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PictureService.class);

    public PictureService() {
        super(FilePicture.TYPE_NAME);

    }

    @Override
    protected boolean isOfTypeAndSet(MediaElement mediaElement, byte[] bytes) {
        FileTypePicture fileTypePicture = new FileTypePicture();
        return fileTypePicture.isOfTypeAndSet(mediaElement, bytes);
    }

    private BufferedImage getScaledInstance(BufferedImage imgIn, int targetWidth, int targetHeight, Integer backgroundColor) {
        BufferedImage complete = new BufferedImage(targetWidth, targetHeight, imgIn.getType());
        // Create quickly, then smooth and brighten it.
        BufferedImage imgScaled = Scalr.resize(imgIn, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);

        Graphics2D graphics = complete.createGraphics();

        graphics.setPaint(new Color(backgroundColor));
        graphics.fillRect(0, 0, complete.getWidth(), complete.getHeight());

        graphics.drawImage(imgScaled, (targetWidth - imgScaled.getWidth()) / 2, (targetHeight - imgScaled.getHeight()) / 2, null);
        //  graphics.finalize();
        return complete;
    }

    /**
     * Create scaled variant file picture.
     *
     * @param media           the media
     * @param width           the width
     * @param height          the height
     * @param overrideType    the override type
     * @param tag             the tag
     * @param readSession     the read session
     * @param backgroundColor the background color
     * @return the file picture
     * @throws IOException the io exception
     */
    public FilePicture createScaledVariant(FilePicture media, Integer width, Integer height, String overrideType, String tag, Session readSession, Integer backgroundColor) throws IOException {

        String suffixWanted = getSuffix(media.getMediaElement().getMediaHostFileName());
        if (null != overrideType) {
            suffixWanted = overrideType;
        }
        //String attrributeName = MediaType.VARIANT + "_" + width + "x" + height;
        String attrributeName = tag;

        if (media.getFirstMediaElementWithAttribute(attrributeName) != null) {
            LOGGER.info("Scaled Media already existant ...");
            return media;
        }
        // find original media element
        {
            // work on a copy, since we modify the list of media inside the loop which would then lead to concurrency
            // error when working on same list...
            // Take always the original element
            MediaElement element = media.getMediaElement();

            // make input stream from host
            // get 2 ftp clients, one for reding, one for saving
            String targetFileName = media.getIdentifier() + "-" + width + "x" + height + "." + suffixWanted;
            String path = getFullPathForMediaProcessed(media, "");

            MediaElement element1 = createMediaElementFromHostFile(path, targetFileName);
            if (element1 != null) {
                // take shorthand variant import

                updateMediaElementAndCheckValidity(element1);
                element1.addAttribute(attrributeName);
                media.getVariants().add(element1);

            } else {

                folderUtil.checkRemoteDirectoriesAndStuffXX(readSession, media);
                InputStream inputStream = readSession.readRaw(element.getMediaHostFileName());
                BufferedImage imgOriginal = ImageIO.read(inputStream);
                //update dimension of original element
                //  element.setDimension(img_original.getWidth(), img_original.getHeight());
                inputStream.close();
                readSession.finalizeRaw();
                // check if original image is bigger than target width
                if ((imgOriginal.getWidth() < width) && (imgOriginal.getHeight() < height)) {
                    LOGGER.info("Source image too small for desired target");
                    return media;
                }
                BufferedImage scaled = getScaledInstance(imgOriginal, width, height, backgroundColor);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(scaled, suffixWanted, outputStream);

                InputStream imageInputStream = new ByteArrayInputStream(outputStream.toByteArray());
                String fullPath2;
                fullPath2 = getFullPathForMediaProcessed(media, targetFileName);

                uploadFileToMediaHostDestinationAndCloseInputStream(fullPath2, imageInputStream);

                // close input and outputstreams
                outputStream.close();
                imageInputStream.close();

                // create media element for variant
                MediaElement mediaElement = new MediaElement();
                mediaElement.setSize(Long.valueOf(outputStream.size()));
                //   mediaElement.setDimension(scaled.getWidth(), scaled.getHeight());
                mediaElement.setMediaFormat(MediaFormat.PICTURE);
                mediaElement.setMediaType(MediaType.VARIANT);
                mediaElement.getAtributes().add(attrributeName);
                mediaElement.getAtributes().add(tag);
                mediaElement.getProperties().put("TYPE", tag);
                mediaElement.setMediaHostFileName(getFullPathForMediaProcessed(media, targetFileName));

                mediaElement.setMediaStatus(MediaStatus.AVAILABLE_ON_MEDIA_HOST);
                // and take all the time we want to update the detailed data
                updateMediaElementAndCheckValidity(mediaElement);
                media.getVariants().add(mediaElement);
            }
            save(media);

        }
        return media;
    }

}
