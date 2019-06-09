package com.froso.ufp.modules.optional.media.service.util;

import com.froso.ufp.modules.optional.media.model.media.*;
import java.io.*;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.pdmodel.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 27.03.2015.
 */
public class FileTypePDF implements FileTypeDeterminer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypePDF.class);

    /**
     * Update media element.
     *
     * @param mediaElement the media element
     * @param bytes        the bytes
     * @return the boolean
     */
    @Override
    public boolean isOfTypeAndSet(MediaElement mediaElement, byte[] bytes) {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            // PDF
            PDDocument pdfDocument = PDDocument.load(byteArrayInputStream);
            if (pdfDocument != null) {

                PDDocumentInformation information = pdfDocument.getDocumentInformation();

                mediaElement.setMediaFormat(MediaFormat.PDF);
                mediaElement.setMediaStatus(MediaStatus.PROCESSED);
                mediaElement.setSize((long) bytes.length);

                mediaElement.setPropertyString(COSName.TITLE.getName(), information.getTitle());
                mediaElement.setPropertyString(COSName.CREATOR.getName(), information.getCreator());
                mediaElement.setPropertyString(COSName.AUTHOR.getName(), information.getAuthor());
                mediaElement.setPropertyString(COSName.CREATION_DATE.getName(), information.getCreationDate());
                mediaElement.setPropertyString(COSName.PAGES.getName(), information.getPropertyStringValue(COSName.PAGES.toString()));

                return true;
            }
            byteArrayInputStream.close();
            return false;
        } catch (IOException e) {
            // catch any exception which means the element is not of that type
            LOGGER.error("Update Information Error: " + e.getMessage(), e);
            return false;
        }
    }
}
