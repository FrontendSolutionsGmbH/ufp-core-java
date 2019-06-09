package com.froso.ufp.modules.optional.media.service.util;

import java.io.*;
import org.apache.commons.net.ftp.*;
import org.slf4j.*;
import org.springframework.integration.file.remote.session.*;

/**
 * This utility class implements a method that removes a non-empty directory
 * on a FTP server.
 *
 * @author www.codejava.net
 */
public class FTPUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FTPUtil.class);

    /**
     * Removes a non-empty directory by delete all its sub files and
     * sub directories recursively. And finally remove the directory.
     *
     * @param ftpClient  the ftp client
     * @param parentDir  the parent dir
     * @param currentDir the current dir
     * @throws IOException the io exception
     */
    public static void removeDirectory(Session<FTPFile> ftpClient, String parentDir,
                                       String currentDir) throws IOException {
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "/" + currentDir;
        }

        FTPFile[] subFiles = ftpClient.list(dirToList);

        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile theFile : subFiles) {
                String currentFileName = theFile.getName();
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    // skip parent directory and the directory itself
                    continue;
                }
                String filePath = parentDir + "/" + currentDir + "/"
                        + currentFileName;
                if (currentDir.equals("")) {
                    filePath = parentDir + "/" + currentFileName;
                }

                if (theFile.isDirectory()) {
                    // remove the sub directory
                    removeDirectory(ftpClient, dirToList, currentFileName);
                } else {
                    // delete the file
                    boolean deleted = ftpClient.remove(filePath);
                    if (deleted) {
                        LOGGER.info("DELETED the file: " + filePath);
                    } else {
                        LOGGER.info("CANNOT delete the file: " + filePath);
                    }
                }
            }

            // finally, remove the directory itself
            boolean removed = ftpClient.rmdir(dirToList);
            if (removed) {
                LOGGER.info("REMOVED the directory: " + dirToList);
            } else {
                LOGGER.info("CANNOT remove the directory: " + dirToList);
            }
        }
    }
}
