package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import org.apache.velocity.runtime.resource.loader.*;

/**
 * Created by ckleinhuix on 24.03.2015.
 */
public final class FileUtil {

    private FileUtil() {
        // private utility class constructor
        throw new UtilityClassInstanciationException();
    }

    /**
     * Gets file.
     *
     * @param fileName the file name
     * @return the file
     * @throws Exception the exception
     */
    public static InputStream getFileInputStream(String fileName) {
        ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader();
        InputStream stream = classpathResourceLoader.getResourceStream(fileName);
        return stream;
    }

    /**
     * Gets file.
     *
     * @param fileName the file name
     * @return the file
     * @throws IOException the io exception
     */
    public static String getFile(String fileName) throws IOException {

        String result;
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(getFileInputStream(fileName), writer, StandardCharsets.UTF_8.name());
            result = writer.toString();
        } catch (Exception e) {
            throw new IOException("Problem Reading " + fileName + " " + e.getMessage(), e);
        }
        return result;
    }
}
