package com.froso.ufp.core.domain.documents.simple.plain;

/**
 * FIXME: TODO: clean up this class and move every color to its own subclass, no monolithic lasses please
 */

import com.fasterxml.jackson.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 30.10.13 Time: 12:49
 *
 * the idea of the design settings vo is to provide customizable color/logo set up for different retailers, in the
 * startup phase this is determined by a primary and secondary color along a logo, in the future we are going to extend
 * this stuff to fully apply the designers ide ....
 */
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class DesignSettings {
    public static final int HEX_BYTES = 2;
    public static final int NUMBER_SYSTEM_HEX_BASE = 16;
    private static final Integer FULL_24_BIT_HEX = 0xFFFFFF;
    private static final String HEX_OUTPUT_FORMAT_STRING = "0x%06X";
    // FIXME: TODO: MOVE TO DEFAULT OBJECT POST CONSTRUCT INITIALISATION in service
    private Integer backgroundColor = 0x007F99;
    private Integer listBackgroundColor1 = 0x007F99;
    private Integer listBackgroundColor2 = 0x266572;
    private Integer keyColor = 0x484F50;
    private Integer textColor = 0xffffff;
    private Integer thumbnailBackgroundColor = 0xffffff;


    /**
     * Gets key color string.
     *
     * @return the key color string
     */
    @JsonProperty("keyColor")
    public String getKeyColorString() {

        return String.format(HEX_OUTPUT_FORMAT_STRING, FULL_24_BIT_HEX & keyColor);
    }

    /**
     * Sets key color string.
     *
     * @param value the value
     */
    @JsonProperty("keyColor")
    public void setKeyColorString(String value) {

        keyColor = Integer.parseInt(value.substring(HEX_BYTES), NUMBER_SYSTEM_HEX_BASE);
    }

    /**
     * Gets first color string.
     *
     * @return the first color string
     */
    @JsonProperty("backgroundColor")
    public String getFirstColorString() {

        return String.format(HEX_OUTPUT_FORMAT_STRING, FULL_24_BIT_HEX & backgroundColor);
    }

    /**
     * Sets first color string.
     *
     * @param value the value
     */
    @JsonProperty("backgroundColor")
    public void setFirstColorString(String value) {

        backgroundColor = Integer.parseInt(value.substring(HEX_BYTES), NUMBER_SYSTEM_HEX_BASE);
    }

    /**
     * Gets second color string.
     *
     * @return the second color string
     */
    @JsonProperty("listBackgroundColor1")
    public String getSecondColorString() {

        return String.format(HEX_OUTPUT_FORMAT_STRING, FULL_24_BIT_HEX & listBackgroundColor1);
    }

    /**
     * Sets second color string.
     *
     * @param value the value
     */
    @JsonProperty("listBackgroundColor1")
    public void setSecondColorString(String value) {

        listBackgroundColor1 = Integer.parseInt(value.substring(HEX_BYTES), NUMBER_SYSTEM_HEX_BASE);
    }

    /**
     * Gets third color string.
     *
     * @return the third color string
     */
    @JsonProperty("listBackgroundColor2")
    public String getThirdColorString() {

        return String.format(HEX_OUTPUT_FORMAT_STRING, FULL_24_BIT_HEX & listBackgroundColor2);
    }

    /**
     * Sets third color string.
     *
     * @param value the value
     */
    @JsonProperty("listBackgroundColor2")
    public void setThirdColorString(String value) {

        listBackgroundColor2 = Integer.parseInt(value.substring(HEX_BYTES), NUMBER_SYSTEM_HEX_BASE);
    }

    /**
     * Gets text color string.
     *
     * @return the text color string
     */
    @JsonProperty("textColor")
    public String getTextColorString() {

        return String.format(HEX_OUTPUT_FORMAT_STRING, FULL_24_BIT_HEX & textColor);
    }

    /**
     * Sets text color string.
     *
     * @param value the value
     */
    @JsonProperty("textColor")
    public void setTextColorString(String value) {

        textColor = Integer.parseInt(value.substring(HEX_BYTES), NUMBER_SYSTEM_HEX_BASE);
    }

    /**
     * Gets key color.
     *
     * @return the key color
     */
    @JsonIgnore
    public Integer getKeyColor() {

        return keyColor;
    }

    /**
     * Sets key color.
     *
     * @param keyColor the key color
     */
    @JsonIgnore
    public void setKeyColor(Integer keyColor) {

        this.keyColor = keyColor;
    }

    /**
     * Gets background color.
     *
     * @return the background color
     */
    @JsonIgnore
    public Integer getBackgroundColor() {

        return backgroundColor;
    }

    /**
     * Sets background color.
     *
     * @param backgroundColor the background color
     */
    @JsonIgnore
    public void setBackgroundColor(Integer backgroundColor) {

        this.backgroundColor = backgroundColor;
    }

    /**
     * Gets list background color 1.
     *
     * @return the list background color 1
     */
    @JsonIgnore
    public Integer getListBackgroundColor1() {

        return listBackgroundColor1;
    }

    /**
     * Sets list background color 1.
     *
     * @param listBackgroundColor1 the list background color 1
     */
    @JsonIgnore
    public void setListBackgroundColor1(Integer listBackgroundColor1) {

        this.listBackgroundColor1 = listBackgroundColor1;
    }

    /**
     * Gets list background color 2.
     *
     * @return the list background color 2
     */
    @JsonIgnore
    public Integer getListBackgroundColor2() {

        return listBackgroundColor2;
    }

    /**
     * Sets list background color 2.
     *
     * @param listBackgroundColor2 the list background color 2
     */
    @JsonIgnore
    public void setListBackgroundColor2(Integer listBackgroundColor2) {

        this.listBackgroundColor2 = listBackgroundColor2;
    }

    /**
     * Gets text color.
     *
     * @return the text color
     */
    @JsonIgnore
    public Integer getTextColor() {

        return textColor;
    }

    /**
     * Sets text color.
     *
     * @param textColor the text color
     */
    @JsonIgnore
    public void setTextColor(Integer textColor) {

        this.textColor = textColor;
    }

    @JsonIgnore
    public Integer getThumbnailBackgroundColor() {
        return thumbnailBackgroundColor;
    }

    @JsonIgnore
    public void setThumbnailBackgroundColor(Integer thumbnailBackgroundColor) {
        this.thumbnailBackgroundColor = thumbnailBackgroundColor;
    }

    @JsonProperty("thumbnailBackgroundColor")
    public String getThumbnailBackgroundColorString() {

        return String.format(HEX_OUTPUT_FORMAT_STRING, FULL_24_BIT_HEX & thumbnailBackgroundColor);
    }

    /**
     * Sets text color string.
     *
     * @param value the value
     */
    @JsonProperty("thumbnailBackgroundColor")
    public void setThumbnailBackgroundColorString(String value) {

        thumbnailBackgroundColor = Integer.parseInt(value.substring(HEX_BYTES), NUMBER_SYSTEM_HEX_BASE);
    }

}
