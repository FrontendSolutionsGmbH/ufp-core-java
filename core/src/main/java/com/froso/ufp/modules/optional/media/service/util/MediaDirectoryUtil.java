package com.froso.ufp.modules.optional.media.service.util;

import com.froso.ufp.modules.optional.media.model.*;
import org.slf4j.*;
import org.springframework.integration.file.remote.session.*;

import java.io.*;

public class MediaDirectoryUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaDirectoryUtil.class);
    private String mainPath;

    public MediaDirectoryUtil(String mainPathIn) {
        this.mainPath = mainPathIn;
    }

    public String getOriginalsPath(UfpFile file) {

        return mainPath + File.separator + file.getId();
    }

    public String getProcessedPath(UfpFile file) {

        return mainPath + File.separator + file.getId();
    }

    public void checkRemoteDirectoriesAndStuffXX(Session session, UfpFile file) throws IOException {
        /*
        if (checkedMainPath) {
            return;
        }
            */
        if (!session.exists(mainPath)) {
            LOGGER.info("Creating Path " + mainPath);
            session.mkdir(mainPath);
        }

        if (!session.exists(mainPath + File.separator + file.getId())) {
            LOGGER.info("Creating Path " + mainPath + File.separator + file.getId());
            session.mkdir(mainPath + File.separator + file.getId());
        }
    }
}
