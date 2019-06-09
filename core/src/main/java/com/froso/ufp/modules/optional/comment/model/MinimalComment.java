package com.froso.ufp.modules.optional.comment.model;

import javax.validation.constraints.*;

/**
 * Created by ckleinhuis on 02.02.2018.
 */
public class MinimalComment implements Comment {

    @NotNull
    private String comment;

    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
