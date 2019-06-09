package com.froso.ufp.modules.optional.facebookbot.model.bot;

import java.util.*;
import org.apache.commons.lang.*;

/**
 * Created by ckleinhuix on 13/08/2016.
 */
public class AnyPayload implements IPayloadCoordinate, IPayloadMedia {
    private String url;
    private String _long;
    private Float lat;
    private String template_type;
    private String text;
    private List<Button> buttons;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets template type.
     *
     * @return the template type
     */
    public String getTemplate_type() {
        return template_type;
    }

    /**
     * Sets template type.
     *
     * @param template_type the template type
     */
    public void setTemplate_type(String template_type) {
        this.template_type = template_type;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = StringUtils.abbreviate(text, 320);
    }

    /**
     * Gets buttons.
     *
     * @return the buttons
     */
    public List<Button> getButtons() {

        return buttons;
    }

    /**
     * Sets buttons.
     *
     * @param buttons the buttons
     */
    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

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
