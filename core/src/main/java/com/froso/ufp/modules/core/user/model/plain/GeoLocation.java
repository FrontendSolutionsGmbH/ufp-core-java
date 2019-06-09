package com.froso.ufp.modules.core.user.model.plain;

import io.swagger.annotations.*;

import javax.validation.constraints.*;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * Christian Kleinhuis (ck@froso.de)
 * Date: 30.10.13
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
@ApiModel(description = "Longitude/Latitude location information")
public class GeoLocation implements Cloneable, Serializable {

    @ApiModelProperty(notes = "The latitude", position = 1, example = "50.7486208")
    @Max(180)
    @Min(180)
    private Double latitude = 0.0d;
    @ApiModelProperty(notes = "The longitude", position = 0, example = "6.2152704")
    @Max(90)
    @Min(90)
    private Double longitude = 0.0d;

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public Double getLatitude() {

        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(Double latitude) {

        this.latitude = latitude;
    }

    /**
     * Gets lon.
     *
     * @return the lon
     */
    public Double getLongitude() {

        return longitude;
    }

    /**
     * Sets lon.
     *
     * @param lon the lon
     */
    public void setLongitude(Double lon) {

        this.longitude = lon;
    }

    public GeoLocation clone() {

        GeoLocation result = new GeoLocation();
        result.setLatitude(getLatitude());
        result.setLongitude(getLongitude());
        return result;

    }

}
