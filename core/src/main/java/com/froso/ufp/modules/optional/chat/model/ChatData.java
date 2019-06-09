package com.froso.ufp.modules.optional.chat.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.user.model.*;
import com.froso.ufp.modules.optional.media.model.*;
import javax.validation.constraints.*;

/**
 * Created by ck on 20.09.2016.
 *
 * @param <T> the type parameter
 */
public abstract class ChatData<T extends ChatRoom> extends AbstractDataDocumentWithCoreUserLink {

    /**
     * The constant TYPE_NAME.
     */
    @NotNull
    private String message;
    private String title;
    private String summary;
    private DataDocumentLink<FilePicture> picture;

    /**
     * Instantiates a new Chat data.
     *
     * @param name the name
     */
    public ChatData(String name) {
        super(name);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public DataDocumentLink<FilePicture> getPicture() {
        return picture;
    }

    public void setPicture(DataDocumentLink<FilePicture> picture) {
        this.picture = picture;
    }

    /**
     * Gets chat room link.
     *
     * @return the chat room link
     */
    public abstract DataDocumentLink<T> getChatRoomLink();

    /**
     * Sets chat room link.
     *
     * @param chatRoomLink the chat room link
     */
    public abstract void setChatRoomLink(DataDocumentLink<T> chatRoomLink);

    /**
     * Gets picture.
     *
     * @return the picture
     */
    public DataDocumentLink<FilePicture> getPictureLink() {
        return picture;
    }

    /**
     * Sets picture.
     *
     * @param picture the picture
     */
    public void setPictureLink(DataDocumentLink<FilePicture> picture) {
        this.picture = picture;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets comment.
     *
     * @param message the comment
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
