package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * Created by ckleinhuix on 13/08/2016.
 */
public class PayloadCoordinate implements IPayloadCoordinate {
    private String _long;
    private Float lat;

    /**
     * Gets long.
     *
     * @return the long
     */
    @Override
    public String getLong() {
        return _long;
    }

    /**
     * Sets long.
     *
     * @param _long the long
     */
    @Override
    public void setLong(String _long) {
        this._long = _long;
    }

    /**
     * Gets lat.
     *
     * @return the lat
     */
    @Override
    public Float getLat() {
        return lat;
    }

    /**
     * Sets lat.
     *
     * @param lat the lat
     */
    @Override
    public void setLat(Float lat) {
        this.lat = lat;
    }
}
