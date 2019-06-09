package com.froso.ufp.modules.optional.chat.model;

import javax.validation.constraints.*;

/**
 * Created by ck on 20.09.2016.
 */
public class ChatRoomCreateData {
    @NotNull
    private String name;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
