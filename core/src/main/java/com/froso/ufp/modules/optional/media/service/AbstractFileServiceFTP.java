package com.froso.ufp.modules.optional.media.service;

import com.froso.ufp.core.configuration.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.applicationproperty.service.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.ftp.model.*;
import com.froso.ufp.modules.optional.ftp.service.*;
import com.froso.ufp.modules.optional.media.model.*;
import com.froso.ufp.modules.optional.media.model.media.*;
import com.froso.ufp.modules.optional.media.service.util.*;
import org.apache.commons.io.*;
import org.apache.commons.net.ftp.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.*;
import org.springframework.integration.file.remote.session.*;
import org.springframework.scheduling.concurrent.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

// TODO: move ftp dependency to this module

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 14:51 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 * <p>
 * all crud method available provided by this service
 * <p>
 * this is a core object, meant for maintain media, mostly images, for images special functions exists, like thumbnails
 * <p>
 * BUT
 * <p>
 * this is the core image maintainer, it is responsible for giving ids and maintaining the integrity of the data ftp
 * based image storing is managed here
 *
 * @param <T> the type parameter
 */
abstract public class AbstractFileServiceFTP<T extends UfpFile> extends AbstractClientRefService<T> implements INamedObject, FileService<T> {

    /**
     * The constant FILE_PROP_NAME_ROOT.
     */
    public static final String FILE_PROP_NAME_ROOT = "ufp.controller.files";
    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "MediaService";
    /**
     * The constant WORKING.
     */
    public static final String WORKING = "working";
    /**
     * The constant FTP_PROTECTED.
     */
    public static final String FTP_PROTECTED = "FTP_PROTECTED";
    /**
     * The constant FTP.
     */
    public static final String FTP = "FTP";
    /**
     * The constant UPDATE_ELEMT_FROM_INPUT_STREAM_THREADED.
     */
    public static final String UPDATE_ELEMT_FROM_INPUT_STREAM_THREADED = "updateElemtFromInputStreamThreaded";
    /**
     * The constant FTP_DEFAULT_CONFIG.
     */
    public static final String FTP_DEFAULT_CONFIG = "Default";
    private static final String DEFAULT_PICTURE_TYPE_DONTUSE = "jpg";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileServiceFTP.class);
    /**
     * The Folder util.
     */
    protected MediaDirectoryUtil folderUtil;
    /**
     * The constant UPDATE_ELEMT_FROM_INPUT_STREAM_THREADED.
     */

    // @Qualifier(FTPConfig.CUBE_BACKEND_OUTGOING_MEDIA_FTP)
    //@Autowired
    //private CachingSessionFactory<FTPClient> ftpSessionFactoryNormal;
    //  @Qualifier(FTPConfig.CUBE_BACKEND_OUTGOING_PROTECTEDMEDIA_FTP)
    // @Autowired
    // private CachingSessionFactory<FTPClient> ftpSessionFactoryProtected;
    private Map<String, Map<String, FTPFile>> sessionFTPFolders = new HashMap();
    private Map<String, List<Session>> activeSessions = new HashMap<>();
    @Autowired
    @Qualifier(ThreadConfig.MEDIUM_PRIO_THREADPOOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private FTPService ftpService;

    @Autowired
    private ApplicationPropertyService applicationPropertyService;

    /**
     * Constructor Simple media service.
     *
     * @param typeName the type name
     */
    public AbstractFileServiceFTP(String typeName) {

        super();
        folderUtil = new MediaDirectoryUtil(typeName);
    }

    /**
     * FIXME: MOVE TO UTILITY CLASS
     * Create id from string string.
     *
     * @param input the input
     * @return the string
     */
    @Deprecated
    public static final String createIDFromString(String input) {

        /**
         * steps:
         *
         * remove all signs other than alphabetic . _ - " "
         *
         * replace all spaces with "-"
         *
         * replace all dots with "-"
         *
         * replace all underlines with "-"
         *
         * return uppercase
         *
         */
        return ("" + input).replaceAll("[^A-Za-z0-9 _\\-.]+", "").replace(" ", "-").replace(".", "-").replace("_", "-").toUpperCase();
    }

    @Override

    protected void prepareDelete(UfpFile object) {
        // template method   delete remote files
        deleteFilesOnRemoteHost(object.getId());

    }

    protected String getFTPConfigPropertyName() {
        return FILE_PROP_NAME_ROOT + "." + typeName.toLowerCase() + ".ftp";
    }

    public String getName() {

        return NAME;

    }

    protected void postProcess(T file, Session session) {
        // template method postprocess after uploading/creation/modification
    }

    @Override
    public ByteArrayResource getByArrayRessource(T mediaElement) throws IOException {
        Session session = null;
        ByteArrayResource result = null;
        try {
            if (mediaElement.getHostType() == HostTypeEnum.PUBLIC) {

                session = createNewSession("getByArrayRessource");
            } else {
                session = createNewProtectedSession("getByArrayRessource");

            }

            InputStream is = session.readRaw(mediaElement.getMediaElement().getMediaHostFileName());
            byte[] bytes = IOUtils.toByteArray(is);
            is.close();
            session.finalizeRaw();

            // return inputstream from the bytes

            result = new ByteArrayResource(bytes);

        } finally {
            finishSession(session);
        }
        return result;
    }

    protected String getFTPPublicPath(T element) {
        // fill out the live path url for the media element, depending on current ftp setting configured

        if (element.getMediaElement() != null) {
            FTPSetting ftpSetting = ftpService.getSettingByName(applicationPropertyService.getPropertyValue(getFTPConfigPropertyName(), FTP_DEFAULT_CONFIG));
            if (ftpSetting != null) {
                String livePath = ftpSetting.getPublicHttpWithPath();
                return livePath;
            }
        }

        return "";
    }

    @Override
    protected void prepareResultElement(T element) {
        // fill out the live path url for the media element, depending on current ftp setting configured

        if (element.getMediaElement() != null) {
            FTPSetting ftpSetting = ftpService.getSettingByName(applicationPropertyService.getPropertyValue(getFTPConfigPropertyName(), FTP_DEFAULT_CONFIG));
            if (ftpSetting != null) {
                String livePath = ftpSetting.getPublicHttpWithPath();
                element.getMediaElement().setLivePath(livePath + "/" + element.getMediaElement().getMediaHostFileName());
            }
        }

    }

    @Override
    public void deleteAll() {

        try {
            Session session = createNewSession("deleteall");
            try {
                // delete just mongo data
                super.deleteAll();
                //repository.deleteAll();
                return;
                // return deleteAllVariants(session);
            } finally {
                finishSession(session);
            }
        } catch (Exception e) {
            LOGGER.debug("Exception on deletall " + e.getMessage(), e);
        }
    }

    protected Session createNewSession(String identifier) throws IOException {

        LOGGER.info("Creating FTP Session for: " + identifier);

        CachingSessionFactory ftpSessionFactory = ftpService.getSessionFactory(applicationPropertyService.getPropertyValue(getFTPConfigPropertyName(), FTP_DEFAULT_CONFIG));

        Session result = ftpSessionFactory.getSession();
        //folderUtil.checkRemoteDirectoriesAndStuffXX(result);
        if (!activeSessions.containsKey(identifier)) {
            activeSessions.put(identifier, new ArrayList<Session>());
        }
        activeSessions.get(identifier).add(result);
        int count = 0;
        // count them for debugging reasons ...
        for (Map.Entry<String, List<Session>> entry : activeSessions.entrySet()) {
            count += entry.getValue().size();

        }
        LOGGER.info("------------------------------------------------> Active Sessions: " + count);
        return result;
    }

    @Deprecated
    private Session createNewProtectedSession(String identifier) throws IOException {
        return createNewSession(identifier);
    }

    protected void finishSession(Session session) {

        for (Map.Entry<String, List<Session>> entry : activeSessions.entrySet()) {
            for (Session sessionCurrent : entry.getValue()) {
                if (sessionCurrent.equals(session)) {
                    LOGGER.info("Finishing FTP Session for: " + entry.getKey());
                    session.close();
                    entry.getValue().remove(session);
                    return;
                }
            }
        }

        //
        LOGGER.warn("No entry found for session ... ");
        if (session != null) {
            session.close();
        }
    }

    T findOneByIdentifier(String identifier) {
        return findOneByKeyValue("identified", identifier);
    }

    T doFindOneByIdentifierAndGroupName(String identifier) {

        Map<String, String> searchMap = new HashMap<>();

        searchMap.put("identifier", identifier);

        return findOneByKeyValues(searchMap);
    }

    @Override
    public String getFullPathForMediaProcessed(T media, String targetFileName) {
        String fullPath2;
        fullPath2 = folderUtil.getProcessedPath(media) + "/" + targetFileName;

        return fullPath2;
    }

    @Override
    public String getFullPathForMediaOriginal(T media, String targetFileName) {
        String fullPath2;

        fullPath2 = folderUtil.getOriginalsPath(media) + "/" + targetFileName;

        return fullPath2;
    }

    public T updateElemtFromInputStreamProtected(final String id, final String url, final InputStream inputStream, final String suggestedID) throws IOException {
        T result = updateElemtFromInputStreamXX(id, url, inputStream, suggestedID, createNewProtectedSession(UPDATE_ELEMT_FROM_INPUT_STREAM_THREADED));
        result.setHostType(HostTypeEnum.PROTECTED);
        save(result);

        return result;

    }

    @Override
    public T updateElemtFromInputStream(final String id, final String url, final InputStream inputStream, final String suggestedID) throws IOException {
        return updateElemtFromInputStreamXX(id, url, inputStream, suggestedID, createNewSession(UPDATE_ELEMT_FROM_INPUT_STREAM_THREADED));

    }

    private T updateElemtFromInputStreamXX(final String id, final String url, final InputStream inputStream, final String suggestedID, Session session) {
        T simpleFile = findOne(id);
        //   T SimpleFile<T>Temporary = findOne(id);
        //   prepareUploadMedia(SimpleFile<T>Temporary);
        // YES: we do store a temporary object in database now, so that each call to the object gives the actual state, only problem is
        // when server fails...
        //   save(SimpleFile<T>Temporary);

        try {

            // we delete variants
            // deleteVariantsOnRemoteHost(SimpleFile<T>, session);
            // and delete original
            // deleteMediaElement(SimpleFile<T>.getMediaElement(), session);

            String fileName = suggestedID + "." + getSuffix(url);

            prepareUploadMedia(simpleFile);
            folderUtil.checkRemoteDirectoriesAndStuffXX(session, simpleFile);

            uploadFileToMediaHostDestinationAndCloseInputStream(getFullPathForMediaOriginal(simpleFile, fileName), inputStream, session);

            simpleFile.getMediaElement().setMediaHostFileName(getFullPathForMediaOriginal(simpleFile, fileName));
            // if all went ok create a copy of current element in media
            //  SimpleFile<T>.getVariants().add(SimpleFile<T>.getMediaElement());

            // set currents element data to the most recent one (the current) !
            fillMediaObject(url, suggestedID, simpleFile, fileName);

            // and let the real file type parser set up the media properties for supported types (image,excel,pdf,...)
            updateMediaElementAndCheckValidity(simpleFile.getMediaElement(), session);

            save(simpleFile);
        } catch (Exception e) {
            LOGGER.debug("Error Occured Updating Media Element " + e.getMessage(), e);
            simpleFile.getMediaElement().setMediaStatus(MediaStatus.ERROR);
            //     throw new IOException("Create Media Element:" + e.getMessage(), e);
        } finally {
            finishSession(session);
        }
        return simpleFile;
    }

    private void deleteFilesOnRemoteHost(String id) {
        UfpFile file = findOne(id);
        Session session = null;
        if (file != null) {

            try {
                session = createNewSession("deleteFilesOnRemoteHost");

                FTPUtil.removeDirectory((Session<FTPFile>) session, folderUtil.getOriginalsPath(file), "");
            } catch (Exception e) {

                LOGGER.error("deleteFilesOnRemoteHost ", e);
            } finally {
                if (session != null) {
                    finishSession(session);
                }
            }
        }

    }

    /**
     * Update elemt from input stream and create thumbnails threaded simple media.
     *
     * @param id          the id
     * @param url         the url
     * @param stream      the stream
     * @param suggestedID the suggested id
     * @return the simple media
     */
    @Override
    public T updateElemtFromInputStreamThreaded(final String id, final String url, final InputStream stream, final String suggestedID) {

        // Fast temporary result output, actual uploading is done in thread ...
        T simpleFile1 = findOne(id);

        prepareUploadMedia(simpleFile1);
        save(simpleFile1);
        // create a thread and return modified temporary SimpleFile<T>
        Runnable updateMediaRunnable = new Runnable() {

            @Override
            public void run() {
                Session session = null;
                try {
                    T simpleFile = findOne(id);

                    deleteFilesOnRemoteHost(id);
                    session = createNewSession("uploadelementfrominputstream");
                    folderUtil.checkRemoteDirectoriesAndStuffXX(session, simpleFile);
                    updateElemtFromInputStream(id, url, stream, suggestedID);
                    // get current version from db
                    simpleFile = findOne(id);

                    preparePostProcessMedia(simpleFile);
                    save(simpleFile);

                    postProcess(simpleFile, session);

                    prepareFinaliseProcessing(simpleFile);
                    save(simpleFile);
                } catch (Exception e) {
                    LOGGER.error("updateElemtFromInputStreamThreaded Error ", e);
                    T simpleFile = findOne(id);

                    simpleFile.getAdditionalProperties().put("ERROR: ", e.getMessage());
                    prepareFinaliseProcessingFailed(simpleFile);
                    save(simpleFile);
                } finally {
                    finishSession(session);
                }
            }
        };

        threadPoolTaskExecutor.execute(updateMediaRunnable);
        T simpleFileResult = findOne(id);

        return simpleFileResult;
    }

    private void prepareUploadMedia(T simpleFile) {

        simpleFile.getMediaElement().setMediaStatus(MediaStatus.UPLOADING_TO_MEDIA_HOST);
        simpleFile.getVariants().clear();
    }

    private void preparePostProcessMedia(T simpleFile) {

        simpleFile.getMediaElement().setMediaStatus(MediaStatus.POST_PROCESSING);

    }

    private void prepareFinaliseProcessing(T simpleFile) {

        simpleFile.getMediaElement().setMediaStatus(MediaStatus.AVAILABLE_ON_MEDIA_HOST);

    }

    private void prepareFinaliseProcessingFailed(T simpleFile) {

        simpleFile.getMediaElement().setMediaStatus(MediaStatus.POST_PROCESSING_FAILED);

    }

    @Deprecated
    protected String getSuffix(String inputFileName) {

        String result = DEFAULT_PICTURE_TYPE_DONTUSE;
        if (inputFileName != null) {
            String suffix = inputFileName.substring(inputFileName.lastIndexOf('.') + 1);
            if ((suffix != null) && (!("".equals(suffix)))) {
                result = suffix;
            }
        }
        return result;
    }

    protected void uploadFileToMediaHostDestinationAndCloseInputStream(String fileNameOnTargetMediaFTPHost, InputStream is, Session session) throws IOException {

        LOGGER.info("Uploading to media host: -->" + fileNameOnTargetMediaFTPHost);

        try {
            // check if file already exists
            session.write(is, fileNameOnTargetMediaFTPHost);
            LOGGER.info("Uploading to media host: -->" + fileNameOnTargetMediaFTPHost + " success");
        } catch (Exception e) {

            LOGGER.info("Uploading to media host: -->" + fileNameOnTargetMediaFTPHost + " failure");
            throw e;
        } finally {
            is.close();
        }
    }

    private void fillMediaObject(final String url, final String suggestedID, final T simpleFile, final String fileName) {

        simpleFile.setName(fileName);
        simpleFile.setSourceURL(url);
        if (simpleFile.getIdentifier() == null) {
            simpleFile.setIdentifier(suggestedID);
        }
        simpleFile.setName(fileName);
        MediaElement mediaElement = new MediaElement();
        mediaElement.setMediaType(MediaType.ORIGINAL);
        mediaElement.setMediaHostFileName(getFullPathForMediaOriginal(simpleFile, fileName));
        mediaElement.setMediaStatus(MediaStatus.AVAILABLE_ON_MEDIA_HOST);
        simpleFile.setMediaElement(mediaElement);

        // simpleFile.getVariants().add(mediaElement);
    }

    @Override
    public T createElemtFromInputStream(String url, InputStream stream, String suggestedID) throws IOException {

        Session session = null;
        try {
            session = createNewSession("createElemtFromInputStream");
            return createElemtFromInputStream(url, stream, suggestedID, session);
        } finally {
            if (session != null) {
                finishSession(session);
            }
        }

    }

    public T createElemtFromInputStreamProtected(String url, InputStream stream, String suggestedID) throws IOException {

        Session session = null;
        try {
            session = createNewProtectedSession("createElemtFromInputStream");
            T element = createElemtFromInputStream(url, stream, suggestedID, session);
            element.setHostType(HostTypeEnum.PROTECTED);
            save(element);
            return element;
        } finally {
            if (session != null) {
                finishSession(session);
            }
        }

    }

    T createInstance() {
        try {
            Type sooper = getClass().getGenericSuperclass();
            Type t = ((ParameterizedType) sooper).getActualTypeArguments()[0];

            return (T) (Class.forName(t.toString()).newInstance());
        } catch (Exception e) {
            return null;
        }
    }

    public T createElemtFromInputStream(String url, InputStream stream, String suggestedID, Session session) throws IOException {

        T result = createInstance();
        try {
            // check if file with that id already exists
            T simpleFile = findByIdentifierAndGroupName(suggestedID);
            // desperately try to avoid uploading, check if file exists remotely
            if (simpleFile == null) {
                simpleFile = createElementFromExistingMediaHostFile(url, suggestedID, session);
            }
            if (simpleFile != null) {
                return simpleFile;
            }
            String fileName = suggestedID + "." + getSuffix(url);
            uploadFileToMediaHostDestinationAndCloseInputStream(folderUtil.getOriginalsPath(simpleFile) + "/" + fileName, stream, session);

            // and store it to database
            fillMediaObject(url, suggestedID, result, fileName);
            // and take all the time we want to update the detailed data
            updateMediaElementAndCheckValidity(result.getMediaElement(), session);

            save(result);
        } catch (Exception e) {
            LOGGER.debug("Error Occured Creating Media Element " + e.getMessage(), e);
            throw new IOException("Create Media Element:" + e.getMessage(), e);
        }
        return result;
    }

    protected MediaElement createMediaElementFromHostFile(String path, String fileName) throws IOException {

        MediaElement result = null;
        try {
            if (isPathFileExistant(path, fileName)) {

                FTPFile file = getPathFile(path, fileName);
                //                InputStream inputStream = session.readRaw(path + "/" + fileName);
                LOGGER.info("Media Element existant on Media-Host " + fileName);
                // Create buffered image to get information
                // found ...
                result = new MediaElement();
                result.setMediaHostFileName(path + "/" + fileName);
                result.setMediaType(MediaType.IMAGE_MEDIA_HOST);
                result.setMediaStatus(MediaStatus.AVAILABLE_ON_MEDIA_HOST);
                result.setSize(file.getSize());
            }
        } catch (Exception e) {
            LOGGER.info("Media Element not existant on Media-Host ... ", e);
        }

        return result;
    }

    private FTPFile getPathFile(String path, String filename) throws IOException {

        Map<String, FTPFile> betterMap = sessionFTPFolders.get(path);
        if (betterMap == null) {
            betterMap = new HashMap<>();
            // Build a list of path contents
            Session session = null;
            try {
                session = createNewSession("getPathFile");
                Object[] ftpfilesasobjectsarray = session.list(path);
                for (int i = 0; i < ftpfilesasobjectsarray.length; i++) {
                    if (ftpfilesasobjectsarray[i] instanceof FTPFile) {
                        FTPFile ftpFile = (FTPFile) ftpfilesasobjectsarray[i];
                        betterMap.put(ftpFile.getName(), ftpFile);
                    }
                }
                LOGGER.info("Putthing path " + path);
                sessionFTPFolders.put(path, betterMap);
            } finally {
                finishSession(session);
            }
        }
        if (sessionFTPFolders != null) {
            return betterMap.get(filename);
        }
        return null;
    }

    private boolean isPathFileExistant(String path, String filename) throws IOException {

        FTPFile temp = getPathFile(path, filename);
        return temp != null;
    }

    @Override
    public T findByIdentifierAndGroupName(String identifier) {

        T simpleFile = null;

        simpleFile = findOneByIdentifier(identifier);

        if (simpleFile != null) {
            LOGGER.info("Existing MediaDB entry found, using! " + "-" + simpleFile.getIdentifier());

        }
        return simpleFile;
    }

    public T createElementFromExistingMediaHostFile(String sourceURL, String suggestedID, Session session) throws IOException {

        T result = null;
        T simpleFile = findByIdentifierAndGroupName(suggestedID);
        if (simpleFile != null) {
            return simpleFile;
        }
        // does not exists in database
        String fileName = suggestedID + "." + getSuffix(sourceURL);
        String path;

        path = folderUtil.getOriginalsPath(simpleFile);

        try {
            if (isPathFileExistant(path, fileName)) {
                //     InputStream inputStream = session.readRaw(pathFull);
                LOGGER.info("Media Element existant on Media-Host " + "/" + fileName);
                FTPFile ftpFile = getPathFile(path, fileName);
                // Create buffered image to get information
                //     BufferedImage img_original = ImageIO.read(inputStream);
                //       inputStream.close();
                //       session.finalizeRaw();
                // found ...
                result = createInstance();
                result.setPictureType(PictureTypeEnum.MEDIA_HOST_REIMPORT);

                result.setIdentifier(suggestedID);
                result.setName(fileName);
                MediaElement mediaElement = createMediaElementFromHostFile(path, fileName);
                mediaElement.setMediaType(MediaType.ORIGINAL);
                mediaElement.setSize(ftpFile.getSize());
                // and add to media element
                //   result.getVariants().add(mediaElement);
                result.setMediaElement(mediaElement);
                //   result.getVariants().add(mediaElement);
                // and take all the time we want to update the detailed data
                // MediaFileTypeUtil.updateMediaElement(result.getMediaElement(), session);
                updateMediaElementAndCheckValidity(result.getMediaElement(), session);
                // and store newly created in db
                save(result);
            }
        } catch (Exception e) {
            LOGGER.info("Media Element not existant on Media-Host ... ");
            // rethrow
            throw e;
        }
        return result;
    }

    void updateMediaElementAndCheckValidity(MediaElement element, Session session) throws IOException {

        InputStream inputStream = session.readRaw(element.getMediaHostFileName());
        byte[] bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();
        session.finalizeRaw();
        isOfTypeAndSet(element, bytes);

    }

    abstract protected boolean isOfTypeAndSet(MediaElement mediaElement, byte[] bytes);

    public T createElementFromUrl(String url, String suggestedID, List<String> tags) throws IOException {

        Session session = null;
        try {

            session = createNewSession("createElementFromUrl");
            T result = createElementFromUrl(url, suggestedID, session);
            result.setTags(tags);
            result = save(result);
            return result;

        } catch (Exception e) {
            LOGGER.error("Problem creating element from url ", e);
        } finally {
            finishSession(session);
        }

        return null;
    }

    public T createElementFromUrl(String url, String suggestedID) {

        Session session = null;
        try {

            session = createNewSession("createElementFromUrl");
            return createElementFromUrl(url, suggestedID, session);

        } catch (Exception e) {
            LOGGER.error("Problem creating element from url ", e);
        } finally {
            finishSession(session);
        }

        return null;
    }

    @Override
    public T createElementFromUrlProtected(String url, String suggestedID) throws
            IOException {

        Session session = null;
        try {

            session = createNewProtectedSession("createElementFromUrl");
            T element = createElementFromUrl(url, suggestedID, session);
            element.setHostType(HostTypeEnum.PROTECTED);
            save(element);
            return element;

        } finally {
            finishSession(session);

        }

    }

    public T createElementFromUrl(String url, String suggestedID, Session
            session) throws IOException {

        String suffix = getSuffix(url);
        String fileName = suggestedID + "." + suffix;
        T simpleFile = createInstance();
        try {
            // check if file with that id already exists
            simpleFile = findByIdentifierAndGroupName(suggestedID);
            // desperately try to avoid uploading, check if file exists remotely

            if (simpleFile != null) {
                return simpleFile;
            }

            simpleFile = createNewDefault();
            simpleFile.setName(url);
            simpleFile.setIdentifier(suggestedID);
            simpleFile.setFileName(url);
            simpleFile = save(simpleFile);

            // Create straight ftp input stream  ....
            InputStream inputStream = new URL(url).openStream();

            // check that group entries exists
            folderUtil.checkRemoteDirectoriesAndStuffXX(session, simpleFile);

            // Upload without any change!

            uploadFileToMediaHostDestinationAndCloseInputStream(folderUtil.getOriginalsPath(simpleFile) + "/" + fileName, inputStream, session);

            // and store it to database
            fillMediaObject(url, suggestedID, simpleFile, fileName);
            save(simpleFile);
            inputStream.close();
            updateMediaElementAndCheckValidity(simpleFile.getMediaElement(), session);
            save(simpleFile);
        } catch (Exception e) {
            LOGGER.debug("Error Occured Creating Media Element " + e.getMessage(), e);
            throw new IOException("Create Media Element:" + e.getMessage(), e);
        }
        return simpleFile;
    }

}
