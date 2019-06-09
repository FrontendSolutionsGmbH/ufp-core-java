package com.froso.ufp.core.domain.interfaces;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * CoreUser: ck
 * Date: 19.03.14
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public interface DataService<T> {
    /**
     * Search paged.
     *
     * @param searchkeyvalues the searchkeyvalues
     * @return the data object list
     */
    DataObjectList searchPaged(Map<String, String> searchkeyvalues);

    /**
     * Find one.
     *
     * @param id the id
     * @return the t
     */
    T findOne(String id);
}
