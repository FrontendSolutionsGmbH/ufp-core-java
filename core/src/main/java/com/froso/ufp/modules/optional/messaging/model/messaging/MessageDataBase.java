package com.froso.ufp.modules.optional.messaging.model.messaging;

import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;

public abstract class MessageDataBase {

    @TextIndexed
    @ApiModelProperty(hidden = false, required = true, notes = "The [parsed] message text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
