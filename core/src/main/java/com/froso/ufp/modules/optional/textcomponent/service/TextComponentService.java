package com.froso.ufp.modules.optional.textcomponent.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import org.apache.commons.lang3.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Created by ckleinhuix on 12.12.13.
 */
@Service
public class TextComponentService extends AbstractClientRefService<TextComponent> {

    @Override
    protected void fillDefaultObject(TextComponent object) {
        // template method, s
        // objectub classes may initialises their objects as they desire...

        object.setKey("defaultComponentKey" + RandomStringUtils.randomNumeric(8));

        //  object.setCode(RandomStringUtils.randomAlphanumeric(8));
        //  object.setMessage("Default Message");

    }

    /**
     * Gets path.
     *
     * @param componentId the component id
     * @return the path
     */
    public String getPath(String componentId) {
        String result = "";
       /*
        String currentID = componentId;
        Boolean first = true;
        while (currentID != null) {

            TextComponent component = findOne(currentID);
            if (component != null) {
                if (first) {
                    first = false;
                    result = component.getName() + result;
                } else {
                    result = component.getName() + "." + result;

                }
                currentID = component.getGroupName();
            }


        }
         */

        return result;

    }

    /**
     * Gets and create if not existant.
     *
     * @param name     the name
     * @param parentID the parent id
     * @return the and create if not existant
     */
    public TextComponent getAndCreateIfNotExistant(String name, String parentID) {

        Map<String, String> search = new HashMap<>();
        search.put("key", name);
        //   search.put("parentID", parentID);

        // Create Parent TextComponent Entry for all Enums
        java.util.List<TextComponent> textComponents = search(search);

        TextComponent textComponent;
        if (textComponents.size() > 0) {
            textComponent = textComponents.get(0);
        } else {
            textComponent = createNewDefault();
            textComponent.setKey(name);
            //   textComponent.setGroupName(parentID);
            save(textComponent);
        }
        return textComponent;
    }

    /**
     * Gets and create if not existant.
     *
     * @param name the name
     * @return the and create if not existant
     */
    public TextComponent getAndCreateIfNotExistant(String name) {

        // fixme: bugme: strange encoding issue
        name = name.replace("Â»", "»");
        name = name.replace("Â«", "«");

        // Create Parent TextComponent Entry for all Enums
        List<TextComponent> textComponents = findByKeyValue("key", "=" + name);

        TextComponent textComponent;
        if (textComponents.isEmpty()) {
            textComponent = createNewDefault();
            textComponent.setKey(name);
            save(textComponent);
        } else {
            textComponent = textComponents.get(0);
        }
        return textComponent;

    }

}
