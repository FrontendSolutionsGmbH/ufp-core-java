package com.froso.ufp.core.service.util.defaultgeneration;

import java.util.regex.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 22.07.2015.
 */
public class DefaultGenerationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGenerationHelper.class);

    public static Integer extractDefaultIndex(String input) {
        try {
            Matcher m = Pattern.compile(".*\\(([0-9]+)\\)").matcher(input);
            while (m.find()) {
                String value = m.group(1);
                return Integer.parseInt(value);
            }
        } catch (Exception e) {

            LOGGER.error("Default Index extraction problem ", e);

        }
        return 0;
    }


    public static String makeDefaultIndexed(String input, Integer index) {
        return input + "(" + index + ")";

    }

    public static Integer extractDefaultIndexEmail(String input) {
        try {
            Matcher m = Pattern.compile(".*default([0-9]+)@.*").matcher(input);
            while (m.find()) {
                String value = m.group(1);
                return Integer.parseInt(value);
            }
        } catch (Exception e) {

            LOGGER.error("Default Index extraction problem ", e);

        }
        return 0;
    }


    public static String makeDefaultIndexedEmail(Integer index) {

        return "default" + index + "@default.com";
    }

}
