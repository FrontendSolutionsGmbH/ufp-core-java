package com.froso.ufp.modules.optional.theme.model;

import io.swagger.annotations.*;

/**
 * Created by ckleinhuis on 27.10.2017.
 */
public class UfpColor {

    @ApiModelProperty(example = "#ffffff",
            required = true)
    private String hex;

    public UfpColor() {

    }

    public UfpColor(String input) {
        this.hex = input;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String fg) {
        // fixme: todo: ensure it is a valid color hex string
        this.hex = fg;
    }


}
