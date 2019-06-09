package com.froso.ufp.modules.optional.authenticate.authenticatefacebook.model;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
public class FacebookData implements IDataObject {
    /**
     * The constant TYPE_NAME.
     */
    @Indexed(unique = true)
    private String facebookId;

    private List<String> additionalIds = new ArrayList<>();
    /* warning the profile picture is used to syncronise various users, when coming from a chat bot the user id is not equal facebooks user id, requesting info will just obtain the profile image url, this can be used to recognize an existing user */
    private String profilePictureName;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public List<String> getAdditionalIds() {
        return additionalIds;
    }

    public void setAdditionalIds(List<String> additionalIds) {
        this.additionalIds = additionalIds;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }
}
