package com.froso.ufp.modules.optional.media.service.util;

import com.froso.ufp.modules.optional.media.model.media.*;
import java.io.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 27.03.2015.
 */
public class FileTypeExcel implements FileTypeDeterminer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypeExcel.class);

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
            // Excel
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(byteArrayInputStream);

            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
            //Get first/desired sheet from the workbook
            HSSFSheet productSheet = workbook.getSheetAt(0);
            LOGGER.trace("Excel Sheet Loaded" + productSheet.getSheetName());
            mediaElement.setProperty("ROWS", String.valueOf(productSheet.getPhysicalNumberOfRows()));
            mediaElement.setMediaFormat(MediaFormat.EXCEL);
            mediaElement.setMediaStatus(MediaStatus.PROCESSED);
            mediaElement.setSize((long) bytes.length);
            byteArrayInputStream.close();
            return true;

        } catch (Exception e) {
            // catch any exception which means the element is not of that type

            LOGGER.info("NOT AN IMAGE", e);
            return false;
        } finally {

        }

    }
}
