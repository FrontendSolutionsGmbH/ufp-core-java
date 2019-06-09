/*
 * Copyright (c) 2015. FroSo, Christian Kleinhuis (ck@froso.de)
 *
 * the request definition service is a method for modules to configure allowed "insecure" urls
 *
 */

package com.froso.ufp.core.service;

import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: ck Date: 16.01.14 Time: 14:12
 * <p>
 * helper service for retrieving properties
 */
@Service
public class RequestDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDefinitionService.class);
    private List<String> tokenFreePathes = new ArrayList<>();
    private List<String> tokenPathes = new ArrayList<>();

    /**
     * Register token free path.
     *
     * @param path the path
     */
    @Deprecated
    public void registerTokenFreePath(String path) {

        tokenFreePathes.add(path);
    }

    public void registerTokenPath(String path) {

        tokenPathes.add(path);
    }

    /**
     * Is path token free boolean.
     *
     * @param path the path
     * @return the boolean
     */
    public boolean isPathTokenFree(String path) {
//        LOGGER.info("IsPath Token Free?",path);
           if(path==null){
               LOGGER.error("IsPath Token Free? path is NULL");
               return false;
           }
//        LOGGER.info("IsPath Token Free?",tokenFreePathes);
        for (String item : tokenFreePathes) {
            if (path.startsWith(item)) {
//                LOGGER.info("IsPath Token Free? YES");
                return true;
            }

        }
//        LOGGER.info("IsPath Token Free? NO");
//

        return false;
    }

}
