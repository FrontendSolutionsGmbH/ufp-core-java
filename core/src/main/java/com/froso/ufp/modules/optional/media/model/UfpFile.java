package com.froso.ufp.modules.optional.media.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.media.model.media.*;
import io.swagger.annotations.*;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UfpFile extends AbstractDataDocumentWithClientLinkAndName {
    @ApiModelProperty("The media element")
    private MediaElement mediaElement = new MediaElement();

    @ApiModelProperty("Public of Protected Host type configuration")
    private HostTypeEnum hostType = HostTypeEnum.PUBLIC;
    private List<MediaElement> variants = new ArrayList<>();
    @ApiModelProperty("The owning user account")
    private DataDocumentLink<ICoreUser> coreUser;
    @ApiModelProperty("if set the live url of the picture")
    private String livePath;
    @ApiModelProperty("A unique identifier for the image")
    private String identifier;
    //
    @ApiModelProperty(notes = "a source url for the file, may point to filesystem resources as well, here for reference")
    private String sourceURL;
    @ApiModelProperty(notes = "The filename of the picture")
    private String fileName;
    @ApiModelProperty(notes = "The MediaState, it is available when its state is MEDIA_HOST and protected is PROTECTED_MEDIA_HOST default is NEW")
    private PictureTypeEnum mediaState = PictureTypeEnum.NEW;

    private List<String> tags = new ArrayList<>();

    public UfpFile(String typeName) {

        super(typeName);
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

    public DataDocumentLink<ICoreUser> getCoreUser() {
        return coreUser;
    }

    public void setCoreUser(DataDocumentLink<ICoreUser> coreUser) {
        this.coreUser = coreUser;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public HostTypeEnum getHostType() {
        return hostType;
    }

    public void setHostType(HostTypeEnum hostType) {
        this.hostType = hostType;
    }

    public PictureTypeEnum getMediaState() {
        return mediaState;
    }

    public void setMediaState(PictureTypeEnum mediaState) {
        this.mediaState = mediaState;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getSourceURL() {

        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {

        this.sourceURL = sourceURL;
    }

    public PictureTypeEnum getPictureType() {

        return mediaState;
    }

    public void setPictureType(PictureTypeEnum pictureType) {

        this.mediaState = pictureType;
    }

    public List<MediaElement> getVariants() {

        return variants;
    }

    public void setVariants(List<MediaElement> variants) {

        this.variants = variants;
    }

    public MediaElement getFirstMediaElementWithAttribute(String attribute) {

        for (MediaElement element : variants) {
            if (element.getAtributes().contains(attribute)) {
                return element;
            }
        }
        return null;
    }

    public MediaElement getMediaElement() {

        return mediaElement;
    }

    public void setMediaElement(MediaElement mediaElement) {

        this.mediaElement = mediaElement;
    }

    public String getLivePath() {

        return livePath;
    }

    public void setLivePath(String livePath) {

        this.livePath = livePath;
    }
}
