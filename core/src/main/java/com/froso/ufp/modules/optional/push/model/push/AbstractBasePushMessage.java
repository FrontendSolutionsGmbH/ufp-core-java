package com.froso.ufp.modules.optional.push.model.push;

import com.froso.ufp.modules.optional.push.model.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. SimpleUser: alex Date: 16.11.13 Time: 13:27 To change this template use File | Settings
 * |
 * File
 * Templates.
 */
public abstract class AbstractBasePushMessage {
    /**
     * The constant KEY_TYPE.
     */
    public static final String KEY_TYPE = "type";
    /**
     * The Default soundfilename.
     */
    public static final String DEFAULT_SOUNDFILENAME = "pushJingle.caf";
    private int badgeValue;
    private String messageTitle = "";
    private String soundFileName;
    private LanguageEnum language;
    private List<PushSendLog> sendLog = new ArrayList<>();

    /**
     * Constructor Base push message.
     *
     * @param lang the language
     */
    public AbstractBasePushMessage(LanguageEnum lang) {

        this.language = lang;
        setSoundFileName(DEFAULT_SOUNDFILENAME);
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }

    public List<PushSendLog> getSendLog() {
        return sendLog;
    }

    public void setSendLog(List<PushSendLog> sendLog) {
        this.sendLog = sendLog;
    }

    /**
     * Gets parameters.
     *
     * @return the parameters
     */
    public abstract Map<String, String> getParameters();

    /**
     * Gets message type.
     *
     * @return the message type
     */
    public abstract String getMessageType();

    /**
     * Gets message title.
     *
     * @return the message title
     */
    abstract public String getMessageTitle();

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
    /**
     * Sets message title.
     *
     * @param messageTitle the message title
     */

    /**
     * Gets badge value.
     *
     * @return the badge value
     */
    public int getBadgeValue() {

        return badgeValue;
    }

    /**
     * Sets badge value.
     *
     * @param badgeValue the badge value
     */
    public void setBadgeValue(int badgeValue) {

        this.badgeValue = badgeValue;
    }

    /**
     * Gets sound file name.
     *
     * @return the sound file name
     */
    public String getSoundFileName() {

        return soundFileName;
    }

    /**
     * Sets sound file name.
     *
     * @param soundFileName the sound file name
     */
    public final void setSoundFileName(String soundFileName) {

        this.soundFileName = soundFileName;
    }

    /**
     * Gets sound file extension.
     *
     * @return the sound file extension
     */
    public String getSoundFileExtensionForAndroid() {

        return "";
    }

    /**
     * Gets sound file extension for ios.
     *
     * @return the sound file extension for ios
     */
    public String getSoundFileExtensionForIOS() {

        return ".caf";
    }
}
