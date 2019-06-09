package com.froso.ufp.modules.optional.userresourcefilter.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.userresourcefilter.model.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserResourceFilterService extends AbstractRepositoryService2<UserResourceFilterModel> {

    /**
     * the parsing happens because certain key values are disturbing mongo, which are:
     * <p>
     * $ dollar sign
     * and
     * . dots
     *
     * @param object the object
     */
    protected void prepareSave(UserResourceFilterModel object) {

        Map<String, Object> newMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : object.getFilter().entrySet()) {

            String newKey = entry.getKey();
            newKey = newKey.replace("$", "_$");
            newKey = newKey.replace(".", "--");

            newMap.put(newKey, entry.getValue());

        }
        object.setFilter(newMap);

    }

    protected void prepareResultElement(UserResourceFilterModel object) {
        // wee need too paarse iiiin thee dollaaar siigns

        Map<String, Object> newMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : object.getFilter().entrySet()) {

            String newKey = entry.getKey();
            newKey = newKey.replace("_$", "$");
            newKey = newKey.replace("--", ".");

            newMap.put(newKey, entry.getValue());

        }
        object.setFilter(newMap);
    }

}
