package com.froso.ufp.modules.optional.textbot.extended.model;

import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("BOT"), @ResourceKeyword("MENU_BOT")
        }), defaultView = @ResourceViewAnnotation(
        sort = @ResourceFilterSortValues(
                @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
        ),
        visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("name"),
                @ResourceVisibleColumn("regex"),
                @ResourceVisibleColumn("answer"),
                @ResourceVisibleColumn("method"),
                @ResourceVisibleColumn("urlToCall")
        }))

)
public class ExtendedTextBot extends ClientReferenceWithName {

    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "ExtendedTextBot";

    private String regex = "light(\\d.*).*";
    private String answer = "trigger alarm $1";
    private String urlToCall = "http://192.168.0.101:8099/light/?duration=$1";
    private String method = "get";

    /**
     * Constructor Simple app device.
     */
    public ExtendedTextBot() {

        super(TYPE_NAME);
    }

    /**
     * Gets regex.
     *
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

    /**
     * Sets regex.
     *
     * @param regex the regex
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * Gets answer.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets answer.
     *
     * @param answer the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets url to call.
     *
     * @return the url to call
     */
    public String getUrlToCall() {
        return urlToCall;
    }

    /**
     * Sets url to call.
     *
     * @param urlToCall the url to call
     */
    public void setUrlToCall(String urlToCall) {
        this.urlToCall = urlToCall;
    }

    /**
     * Gets method.
     *
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets method.
     *
     * @param method the method
     */
    public void setMethod(String method) {
        this.method = method;
    }
}
