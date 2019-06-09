package com.froso.ufp.modules.optional.theme.model;

import io.swagger.annotations.*;

/**
 * Created by ckleinhuis on 27.10.2017.
 */
public class Colors {

    @ApiModelProperty(example = "#ffffff",
            required = true)
    private String fg;
    @ApiModelProperty(example = "#000000",
            required = true)
    private String bg;

    public String getFg() {
        return fg;
    }

    public void setFg(String fg) {
        this.fg = fg;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }
}
