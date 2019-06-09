package com.froso.ufp.modules.optional.media.service.util;

import com.froso.ufp.modules.optional.media.model.media.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 27.03.2015.
 */
public class FileTypeZipTemplate implements FileTypeDeterminer {

    /**
     * The constant TEMPLATE_JSON.
     */
    public static final String TEMPLATE_JSON = "template.json";
    /**
     * The constant TEMPLATES.
     */
    public static final String TEMPLATES = "TEMPLATES";
    /**
     * The constant DIRECTORIES.
     */
    public static final String DIRECTORIES = "DIRECTORIES";
    /**
     * The constant FILES.
     */
    public static final String FILES = "FILES";
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTypeZipTemplate.class);

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
            List<String> files = new ArrayList<>();
            List<String> directories = new ArrayList<>();
            List<String> templates = new ArrayList<>();

            ZipInputStream zis;

            //get the zip file content
            zis = new ZipInputStream(byteArrayInputStream);
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            Boolean found = Boolean.FALSE;
            while (ze != null) {

                String fileName = ze.getName();


                LOGGER.info("ZIP Content: " + fileName);

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                if (fileName.equals(TEMPLATE_JSON)) {
                    // it is a designated template zip for ufp
                    found = Boolean.TRUE;

                }


                if (fileName.endsWith("/")) {
                    directories.add(fileName);
                } else if (fileName.endsWith(".vm")) {
                    templates.add(fileName);
                } else {
                    files.add(fileName);
                }
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
            mediaElement.setProperty(FILES, files);
            mediaElement.setProperty(DIRECTORIES, directories);
            mediaElement.setProperty(TEMPLATES, templates);

            return found;
        } catch (IOException ex) {
            LOGGER.info("Not File Type ZIP", ex);
            return false;


        }

    }
}
