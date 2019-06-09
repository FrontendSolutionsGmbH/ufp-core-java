package com.froso.ufp.modules.optional.push.model;

import io.swagger.annotations.*;

/**
 * Created by ckleinhuis on 05.12.2016.
 */
public class AppDeviceTest {

    @ApiModelProperty(example = "1")
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
