package com.froso.ufp.modules.optional.push.model.push;

import com.froso.ufp.modules.optional.push.model.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * SimpleUser: alex
 * Date: 16.11.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class SuggestionReceivedPushMessage extends AbstractBasePushMessage {
    private static final String KEY_NOTEID = "suggestionId";
    private static final String KEY_USERID = "userId";
    private static final String MP3_FILENAME = AbstractBasePushMessage.DEFAULT_SOUNDFILENAME;
    private String suggestionId;
    private String userId;

    /**
     * Constructor Suggestion received push message.
     */
    public SuggestionReceivedPushMessage() {

        super(LanguageEnum.GERMAN);
        setSoundFileName(MP3_FILENAME);

    }

    public SuggestionReceivedPushMessage(String Message) {

        super(LanguageEnum.GERMAN);
        setSoundFileName(MP3_FILENAME);
    }

    public String getMessageTitle() {
        return "Sie haben eine Empfehlung erhalten";
    }

    /**
     * Gets suggestion id.
     *
     * @return the suggestion id
     */
    public String getSuggestionId() {

        return suggestionId;
    }

    /**
     * Sets suggestion id.
     *
     * @param suggestionId the suggestion id
     */
    public void setSuggestionId(String suggestionId) {

        this.suggestionId = suggestionId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {

        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {

        this.userId = userId;
    }

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    @Override
    public Map<String, String> getParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put(KEY_NOTEID, suggestionId);
        map.put(KEY_USERID, userId);
        return map;
    }

    /**
     * Gets message type.
     *
     * @return the message type
     */
    @Override
    public String getMessageType() {

        return PushMessageTypeEnum.SUGGESTION_RECEIVED.toString();
    }
}
