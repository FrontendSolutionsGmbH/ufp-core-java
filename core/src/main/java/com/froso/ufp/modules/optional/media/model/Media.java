package com.froso.ufp.modules.optional.media.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.optional.media.model.media.*;
import java.util.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 09.11.13 Time: 14:13 To change this
 * template use File | Settings | File Templates.
 */
/*
the ignoreproperties are used to ignore read only properties when loading data
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = Media.TYPE_NAME)
@CompoundIndexes({@CompoundIndex(def = "{'identifier':1,'groupName':1}", name = "identifier_groupname_index", unique = true, dropDups = true, background = true)})
public class Media extends AbstractDataDocument {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "Media";

    @Indexed(unique = false, direction = IndexDirection.DESCENDING, sparse = true, dropDups = true, background = true)
    private String name;
    @Indexed(unique = false, direction = IndexDirection.DESCENDING, sparse = true, dropDups = true, background = true)
    private String groupName = "default";
    private MediaElement mediaElement = new MediaElement();

    private HostTypeEnum hostType = HostTypeEnum.PUBLIC;
    private List<MediaElement> mediaElements = new ArrayList<>();
    @Indexed(unique = false, direction = IndexDirection.DESCENDING, sparse = true, dropDups = true, background = true)
    private String identifier = "identifier";
    // the path on the remote system where the media is actually deliverd to the end-client
    private String livePath;
    //
    private String sourceURL;
    private String fileName;
    private PictureTypeEnum mediaState = PictureTypeEnum.MEDIA_HOST;

    /**
     * Constructor Simple media.
     */
    public Media() {

        super(TYPE_NAME);
        // id setting strategy it is desirable to follow an id naming strategy to reuse existing data ...
    }

    /**
     * extract the file suffix from a file name
     *
     * @param filename the filename
     * @return file suffix
     */
    public static String getFileSuffix(String filename) {

        String result = "";
        if (filename != null) {
            if (-1 != filename.lastIndexOf('.')) {
                result = filename.substring(filename.lastIndexOf('.'));
            }
        }
        return result;
    }

    /**
     * Gets host type.
     *
     * @return the host type
     */
    public HostTypeEnum getHostType() {
        return hostType;
    }

    /**
     * Sets host type.
     *
     * @param hostType the host type
     */
    public void setHostType(HostTypeEnum hostType) {
        this.hostType = hostType;
    }

    /**
     * Gets media state.
     *
     * @return the media state
     */
    public PictureTypeEnum getMediaState() {
        return mediaState;
    }

    /**
     * Sets media state.
     *
     * @param mediaState the media state
     */
    public void setMediaState(PictureTypeEnum mediaState) {
        this.mediaState = mediaState;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets group name.
     *
     * @return the group name
     */
    public String getGroupName() {

        return groupName;
    }

    /**
     * Sets group name.
     *
     * @param groupName the group name
     */
    public void setGroupName(String groupName) {

        this.groupName = groupName;
    }

    /**
     * Gets identifier.
     *
     * @return the identifier
     */
    public String getIdentifier() {

        return identifier;
    }

    /**
     * Sets identifier.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    /**
     * Gets file name.
     *
     * @return the file name
     */
    public String getFileName() {

        return fileName;
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     */
    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    /**
     * Gets source uRL.
     *
     * @return the source uRL
     */
    public String getSourceURL() {

        return sourceURL;
    }

    /**
     * Sets source uRL.
     *
     * @param sourceURL the source uRL
     */
    public void setSourceURL(String sourceURL) {

        this.sourceURL = sourceURL;
    }

    /**
     * Gets picture type.
     *
     * @return the picture type
     */
    public PictureTypeEnum getPictureType() {

        return mediaState;
    }

    /**
     * Sets picture type.
     *
     * @param pictureType the picture type
     */
    public void setPictureType(PictureTypeEnum pictureType) {

        this.mediaState = pictureType;
    }

    /**
     * Gets media elements.
     *
     * @return the media elements
     */
    public List<MediaElement> getMediaElements() {

        return mediaElements;
    }
           /*
    public static SimpleMedia createByClassPath(String name, String id) throws IOException {
        SimpleMedia pictureElement = new SimpleMedia();
        pictureElement.setWorkflowName(name);
        pictureElement.setPictureID(id);
        pictureElement.setPictureType(PictureTypeEnum.RESOURCE);
        pictureElement.setFileSize(pictureElement.classPathResourceIfMediaTypeCorrect().contentLength());
        pictureElement.setFileDate(
                new DateTime(
                        pictureElement.classPathResourceIfMediaTypeCorrect().lastModified(), DateTimeZone.createNewDefault()
                )
        );
        return pictureElement;
    }
    */

    /**
     * Sets media elements.
     *
     * @param mediaElements the media elements
     */
    public void setMediaElements(List<MediaElement> mediaElements) {

        this.mediaElements = mediaElements;
    }

    /**
     * Gets first media element with attribute.
     *
     * @param attribute the attribute
     * @return the first media element with attribute
     */
    public MediaElement getFirstMediaElementWithAttribute(String attribute) {

        for (MediaElement element : mediaElements) {
            if (element.getAtributes().contains(attribute)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Gets media element.
     *
     * @return the media element
     */
    public MediaElement getMediaElement() {

        return mediaElement;
    }

    /**
     * Sets media element.
     *
     * @param mediaElement the media element
     */
    public void setMediaElement(MediaElement mediaElement) {

        this.mediaElement = mediaElement;
    }

    /**
     * Gets live path.
     *
     * @return the live fullpa
     */
    public String getLivePath() {

        return livePath;
    }

    /**
     * Sets live path.
     *
     * @param livePath the live path
     */
    public void setLivePath(String livePath) {

        this.livePath = livePath;
    }
}
