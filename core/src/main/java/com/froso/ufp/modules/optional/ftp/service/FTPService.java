package com.froso.ufp.modules.optional.ftp.service;

import com.froso.ufp.core.exceptions.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import com.froso.ufp.modules.optional.ftp.configuration.*;
import com.froso.ufp.modules.optional.ftp.controller.*;
import com.froso.ufp.modules.optional.ftp.model.*;
import org.apache.commons.net.ftp.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.integration.file.remote.session.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:41 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class FTPService extends AbstractRepositoryService2<FTPSetting> {

    /**
     * The constant CURRENT_DIR.
     */
    private static final String CURRENT_DIR = ".";
    /**
     * The constant PARENT_DIR.
     */
    private static final String PARENT_DIR = "..";
    /**
     * The constant UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_HOST.
     */
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_HOST = "ufp.modules.optional.ftp.default.host";
    /**
     * The constant UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PATH.
     */
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PATH = "ufp.modules.optional.ftp.default.path";
    /**
     * The constant UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PUBLICHTTP.
     */
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PUBLICHTTP = "ufp.modules.optional.ftp.default.publichttp";
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_DIRECTORY = "ufp.modules.optional.ftp.default.directory";
    /**
     * The constant UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_USERNAME.
     */
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_USERNAME = "ufp.modules.optional.ftp.default.username";
    /**
     * The constant UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PASSWORD.
     */
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PASSWORD = "ufp.modules.optional.ftp.default.password";
    /**
     * The constant UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PORT.
     */
    private static final String UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PORT = "ufp.modules.optional.ftp.default.port";
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPService.class);
    @Autowired
    private PropertyServiceRepositoryImpl propertyServiceRepository;

    /**
     * Ensure default entry.
     */
    @PostConstruct
    public void ensureDefaultEntry() {

        if (findByKeyValue("name", "default") == null) {

            FTPSetting settingDefault = createNewDefault();
            save(settingDefault);
        }

    }

    /**
     * it is not abstract because it is not mandatory
     *
     * @param object the object
     */
    @Override
    protected void fillDefaultObject(FTPSetting object) {
        // template method, sub classes may initialises their objects as they desire...
        object.setName(propertyServiceRepository.getPropertyValue("Default"));
        object.setHost(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_HOST));
        object.setDirectory(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PATH));
        object.setPublicHttp(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PUBLICHTTP));
        object.setUserName(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_USERNAME));
        object.setUserPassword(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PASSWORD));
        object.setPort(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PORT));

    }

    /**
     * Gets setting by name.
     *
     * @param name the name
     * @return the setting by name
     */
    public FTPSetting getSettingByName(String name) {
        FTPSetting result = findOneByKeyValue("name", name);
        if (result == null) {
            result = getDefaultFTPSetting();
        }
        return result;
    }

    /**
     * Gets ftp dir info.
     *
     * @param id   the id
     * @param path the path
     * @return the ftp dir info
     * @throws IOException the io exception
     */
    public List<MyFTPFile> getFtpDirList(String id, String path) throws IOException {

        FTPSetting setting = findOne(id);
        CachingSessionFactory factory = getSessionFactoryById(id);
        Session<org.apache.commons.net.ftp.FTPFile> session = factory.getSession();

        org.apache.commons.net.ftp.FTPFile[] files = session.list(path);
        session.close();
        List<MyFTPFile> result = new ArrayList<>();
        for (FTPFile file : files) {
            MyFTPFile fileNew = new MyFTPFile();
            fileNew.setName(file.getName());
            fileNew.setGroup(file.getGroup());
            fileNew.setLink(file.getLink());
            fileNew.setUser(file.getUser());
            fileNew.setHardLinkCount(file.getHardLinkCount());
            fileNew.setTimestamp(file.getTimestamp());
            fileNew.setType(file.getType());
            fileNew.setSize(file.getSize());
            if (fileNew.isFile()) {

                fileNew.setLiveURL(setting.getPublicHttp() + "/" + setting.getDirectory() + "/" + path + "/" + file.getName());

            }
            if (!CURRENT_DIR.equals(file.getName()) && (!PARENT_DIR.equals(file.getName()))) {
                result.add(fileNew);
            }
        }
        return result;
    }

    /**
     * Gets session factory.
     *
     * @param id the id
     * @return the session factory
     */

    /**
     * Gets session factory by id.
     *
     * @param id the id
     * @return the session factory by id
     */
    private CachingSessionFactory getSessionFactoryById(String id) {
        LOGGER.info("FTP SESSION BY ID:" + id);
        FTPSetting config = findOneBrute(id);

        if (config == null) {
            LOGGER.warn("NO FTP SETTING FOUND");
            LOGGER.warn("Using Default FTP Setting");
            config = getDefaultFTPSetting();
        }

        if (config != null) {
            LOGGER.info("FTP SETTING FOUND:" + config.toString());
            // Create FTP Session factory with our wrapper to set working directory
            FTPSessionFactoryMy result = new FTPSessionFactoryMy(config.getDirectory());
            result.setFileType(FTP.BINARY_FILE_TYPE);
            result.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
            result.setPort(Integer.parseInt(config.getPort()));
            result.setHost(config.getHost());
            result.setUsername(config.getUserName());
            result.setPassword(config.getUserPassword());

            // prepare resulting cachingsessionfactory for reusing existing connections
            CachingSessionFactory result2 = new CachingSessionFactory(result);
            result2.setPoolSize(10);
            result2.setSessionWaitTimeout(1000);
            return result2;
        } else {

            LOGGER.warn("NO FTP SETTING FOUND");
        }
        // todo fixme never return null
        return null;
    }

    private FTPSetting getDefaultFTPSetting() {

        FTPSetting result = new FTPSetting();
        result.setHost(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_HOST));
        result.setPublicHttp(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PUBLICHTTP));
        result.setDirectory(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_DIRECTORY));
        result.setPort(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PORT));
        result.setUserName(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_USERNAME));
        result.setUserPassword(propertyServiceRepository.getPropertyValue(UFP_MODULES_OPTIONAL_FTPSETTING_DEFAULT_PASSWORD));

        LOGGER.info("Default ftp setting is " + result.toString());
        return result;
    }

    /**
     * Gets session factory.
     *
     * @param name the name
     * @return the session factory
     */
    public CachingSessionFactory getSessionFactory(String name) {

        LOGGER.warn("getSessionFactory CALLED WITHOUT CLIENT this is deprecated " + name);
        // Retrieve FTP Setting from database
        FTPSetting config = findOneByKeyValue("name", name);

        if (config == null) {
            LOGGER.warn("NO FTP SETTING FOUND");
            LOGGER.warn("Using Default FTP Setting");
            config = getDefaultFTPSetting();
        }

        if (null != config) {
            return getSessionFactoryById(config.getId());
        } else {
            {
                throw new UFPRuntimeException("no FTP configuration found " + name);
                //LOGGER.warn("NOT FTP Config configured, please create an entry in FTPConfig collection");
            }
        }

        // hmm best thing to return here ? certainly not :* fixme: todo:
        //return null;
    }

}
