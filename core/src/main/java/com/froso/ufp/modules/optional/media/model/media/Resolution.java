package com.froso.ufp.modules.optional.media.model.media;

/**
 * Created by ckleinhuix on 15.03.2015.
 */
public class Resolution {
    private Integer width = 0;
    private Integer height = 0;

    /**
     * Gets width.
     *
     * @return the width
     */
    public Integer getWidth() {

        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(Integer width) {

        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public Integer getHeight() {

        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(Integer height) {

        this.height = height;
    }

    /**
     * Sets dimension.
     *
     * @param widthIn  the width in
     * @param heightIn the height in
     */
    public void setDimension(Integer widthIn,
                             Integer heightIn) {

        width = widthIn;
        height = heightIn;
    }
}
