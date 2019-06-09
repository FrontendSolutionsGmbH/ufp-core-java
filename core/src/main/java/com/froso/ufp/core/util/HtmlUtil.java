package com.froso.ufp.core.util;

import java.util.*;
import org.springframework.web.util.*;

/**
 * Created by ckleinhuix on 23.12.2014.
 */
public class HtmlUtil {

    /**
     * Escape html text only.
     *
     * @param input the input
     * @return the string
     */
    public static String escapeHtmlTextOnly(String input) {

        StringBuilder builder = new StringBuilder();
        String currentTag = "";
        String currentText = "";
        String currentVelocity = "";
        boolean insideTag = false;
        boolean velocityCommand = false;
        for (int i = 0; i < input.length(); i++) {
            String current = "" + input.charAt(i);
            if ("#".equals(current)) {
                // VELOCITY COMMAND/COMMENT
                velocityCommand = true;
                currentVelocity = "";
            } else if ("\n".equals(current) && velocityCommand) {
                // view velocity command as ended upon newline
                builder.append(currentVelocity);
                current = "";
                velocityCommand = false;
            } else if ("<".equals(current)) {
                // Tag opens
                insideTag = true;
                String escaped = HtmlUtils.htmlEscape(currentText);
                builder.append(escaped);
                currentText = "";
                currentTag = "";
            }
            if (insideTag) {
                currentTag = currentTag + current;
            } else if (velocityCommand) {
                currentVelocity += current;
            } else {
                currentText = currentText + current;
            }
            if (">".equals(current)) {
                // tag closes
                insideTag = false;
                builder.append(currentTag);
            }
        }
        builder.append(currentText);
        return builder.toString();
    }

    public static String htmlExtractUnescapedTextOnly(String input) {

        Set<String> forbiddenTags = new HashSet<>();
        forbiddenTags.add("script");
        forbiddenTags.add("style");


        StringBuilder builder = new StringBuilder();
        String currentTag = "";
        String currentText = "";
        String currentVelocity = "";
        boolean insideTag = false;
        boolean velocityCommand = false;
        for (int i = 0; i < input.length(); i++) {
            String current = "" + input.charAt(i);
            if ("#".equals(current)) {
                // VELOCITY COMMAND/COMMENT
                velocityCommand = true;
                currentVelocity = "";
            } else if ("\n".equals(current) && velocityCommand) {
                // view velocity command as ended upon newline
                builder.append(currentVelocity);
                current = "";
                velocityCommand = false;
            } else if ("<".equals(current)) {
                // Tag opens
                insideTag = true;
                String escaped = HtmlUtils.htmlUnescape(currentText);
                builder.append(escaped);
                currentText = "";
                currentTag = "";
            }
            if (insideTag) {
                currentTag = currentTag + current;
            } else if (velocityCommand) {
                currentVelocity += current;
            } else {
                if (!isForbiddenTag(forbiddenTags, currentTag)) {
                    currentText = currentText + current;
                }
            }
            if (">".equals(current)) {
                // tag closes
                insideTag = false;
                // ommit outputting of tags
                //builder.append(currentTag);
            }
        }
        builder.append(HtmlUtils.htmlUnescape(currentText));
        return builder.toString();
    }

    private static Boolean isForbiddenTag(Set<String> forbidden, String totest) {

        for (String string : forbidden) {

            if (totest.contains(string)) {
                return Boolean.TRUE;
            }

        }
        return Boolean.FALSE;

    }
}
