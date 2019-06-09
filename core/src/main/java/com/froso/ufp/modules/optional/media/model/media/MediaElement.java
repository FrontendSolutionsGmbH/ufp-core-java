package com.froso.ufp.modules.optional.media.model.media;

import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import io.swagger.annotations.*;
import org.joda.time.*;
import org.springframework.data.annotation.*;

import java.util.*;

public class MediaElement {
    @ApiModelProperty("Dynamic attributes like HEIGHT, WIDTH")
    private Set<String> atributes = new HashSet<>();
    @ApiModelProperty("Tells about its post upload processing status and availability")
    private MediaStatus mediaStatus = MediaStatus.UNPROCESSED;
    @ApiModelProperty("The mediaType is deprecated ")
    private MediaType mediaType = MediaType.ORIGINAL;
    @ApiModelProperty("The media file format (guessed from loaders)")
    private MediaFormat mediaFormat = MediaFormat.UNKNOWN;
    @ApiModelProperty("The filename and path on the media host server, ")
    private String mediaHostFileName;
    //private Resolution          dimension  = new Resolution();
    // FileSize and Filedate are used to compare versions...
    @ApiModelProperty("The file size in bytes")
    private Long size = 0L;
    @ApiModelProperty("Creation date")
    private DateTime date = DateTime.now();
    @ApiModelProperty("additional properties")
    private Map<String, Object> properties = new HashMap<>();
    @ApiModelProperty(notes = "Dynamically generated live path url of the media file", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String livePath;

    @Transient
    public String getLivePath() {
        return livePath;
    }

    @Transient
    public void setLivePath(String livePath) {
        this.livePath = livePath;
    }

    public void setProperty(String name, Object value) {

        getProperties().put(name, value);

    }

    public void setPropertyString(String name, Object value) {

        if (value != null) {
            getProperties().put(name, value.toString());
        }
    }

    public Map<String, Object> getProperties() {

        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void addAttribute(String name) {

        getAtributes().add(name);
    }

    public Set<String> getAtributes() {

        return atributes;
    }

    public void setAtributes(Set<String> atributes) {

        this.atributes = atributes;
    }

    public MediaFormat getMediaFormat() {

        return mediaFormat;
    }

    public void setMediaFormat(final MediaFormat mediaFormat) {

        this.mediaFormat = mediaFormat;
    }

    public MediaStatus getMediaStatus() {

        return mediaStatus;
    }

    public void setMediaStatus(MediaStatus mediaStatus) {

        this.mediaStatus = mediaStatus;
    }

    public MediaType getMediaType() {

        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {

        this.mediaType = mediaType;
    }

    public Long getSize() {

        return size;
    }

    public void setSize(Long size) {

        this.size = size;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getDate() {

        return date;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setDate(DateTime fileDate) {

        this.date = fileDate;
    }

    public String getMediaHostFileName() {

        return mediaHostFileName;
    }

    public void setMediaHostFileName(String mediaHostFileName) {

        this.mediaHostFileName = mediaHostFileName;
    }
}
