package com.froso.ufp.modules.optional.media.service;

import com.froso.ufp.core.service.*;
import org.springframework.core.io.*;

import java.io.*;
import java.util.*;

/**
 * Created by ckleinhuix on 25.12.2015.
 *
 * @param <T> the type parameter
 */
public interface FileService<T extends com.froso.ufp.modules.optional.media.model.UfpFile> extends RepositoryService<T> {
    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets by array ressource.
     *
     * @param mediaElement the media element
     * @return the by array ressource
     * @throws IOException the io exception
     */
    ByteArrayResource getByArrayRessource(T mediaElement) throws IOException;

    void deleteAll();

    /**
     * Gets full path for media processed.
     *
     * @param media          the media
     * @param targetFileName the target file name
     * @return the full path for media processed
     */
    String getFullPathForMediaProcessed(T media, String targetFileName);

    /**
     * Gets full path for media original.
     *
     * @param media          the media
     * @param targetFileName the target file name
     * @return the full path for media original
     */
    String getFullPathForMediaOriginal(T media, String targetFileName);


//    T updateElemtFromInputStreamProtected(String id, String url, InputStream inputStream, String suggestedID) throws IOException;


    T updateElemtFromInputStream(String id, String url, InputStream inputStream, String suggestedID) throws IOException;


    T updateElemtFromInputStreamThreaded(String id, String url, InputStream stream, String suggestedID) throws IOException;

    T createElemtFromInputStream(String url, InputStream stream, String suggestedID) throws IOException;


//    T createElemtFromInputStreamProtected(String url, InputStream stream, String suggestedID) throws IOException;

    T findByIdentifierAndGroupName(String identifier);

//    T createElementFromExistingMediaHostFile(String sourceURL, String suggestedID) throws IOException;

    T createElementFromUrl(String url, String suggestedID) throws IOException;

    T createElementFromUrl(String url, String suggestedID, List<String> tags) throws IOException;

    T createElementFromUrlProtected(String url, String suggestedID) throws
            IOException;

//    /**
//     * Create element from url simple file.
//     *
//     * @param url         the url
//     * @param suggestedID the suggested id
//     * @param session     the session
//     * @return the simple file
//     * @throws IOException the io exception
//     */
//    T createElementFromUrl(String url, String suggestedID, Session
//            session) throws IOException;

}
