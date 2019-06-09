package com.froso.ufp.modules.optional.facebookbot.service;

import com.froso.ufp.modules.core.applicationproperty.interfaces.*;
import com.froso.ufp.modules.optional.facebookbot.annotations.*;
import com.froso.ufp.modules.optional.facebookbot.model.textbot.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ck on 16.08.2016.
 */
@Service
public class FacebookBotMethodService {

    public static final String UFP_MODULES_OPTIONAL_TEXTBOT_ENABLED = "ufp.modules.optional.textbot.enabled";
    public static final String UFP_MODULES_OPTIONAL_TEXTBOT_DISABLED_MESSAGE = "ufp.modules.optional.textbot.disabledMessage";
    public static final String UFP_MODULES_OPTIONAL_TEXTBOT_UNHANDLED_MESSAGE = "ufp.modules.optional.textbot.unhandledMessage";
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookBotMethodService.class);
    private List<Object> calls = new ArrayList<>();
    @Autowired
    private IPropertyService propertyService;

    public void registerBotHandler(Object method) {
        calls.add(method);

    }

    public List<Method> getMethods() {
        List<Method> result = new ArrayList();
        for (Object bean : calls) {
            for (Method method : bean.getClass().getMethods()) {
                if (method.isAnnotationPresent(TextBotMethodAnnotation.class)) {
                    result.add(method);
                }

            }
        }
        return result;
    }

    public void handleMessage(String message) {


    }

    public TextBotResult handleByString(String message, Map<String, Object> data) {

        LOGGER.info("Trying to handle incoming string [" + message + "]");
        if (message == null) {
            return null;
        }


        if (!propertyService.getPropertyValueBoolean(UFP_MODULES_OPTIONAL_TEXTBOT_ENABLED)) {


            return new TextBotStringResult(propertyService.getPropertyValue(UFP_MODULES_OPTIONAL_TEXTBOT_DISABLED_MESSAGE));
        }


        for (Object bean : calls) {

            if (bean.getClass().isAnnotationPresent(TextBotAnnotation.class)) {

                for (Method method : bean.getClass().getMethods()) {
                    if (method.isAnnotationPresent(TextBotMethodAnnotation.class)) {
                        TextBotMethodAnnotation ta = method.getAnnotation(TextBotMethodAnnotation.class);

                        LOGGER.info("Trying to handle incoming string pattern to match try [" + ta.regex() + "]");
                        LOGGER.info("Trying to handle incoming string [" + message + "]");
                        Pattern p = Pattern.compile(ta.regex());
                        Matcher m = p.matcher(message);
                        if (m.matches()) {
                            try {
                                TextBotResult result = (TextBotResult) method.invoke(bean, message, data);
                                if (result != null) {
                                    return result;
                                }
                            } catch (Exception e) {
                                LOGGER.error("FB Error", e);
//                                LOGGER.error(ExceptionUtils.getRootCause(e));
                                return new TextBotStringResult(e.getMessage());
                            }
                        }
                    }
                }
            }

        }


        return new TextBotStringResult(propertyService.getPropertyValue(UFP_MODULES_OPTIONAL_TEXTBOT_UNHANDLED_MESSAGE));

    }
}
