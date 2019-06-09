package com.froso.ufp.core.service.util.query;

import java.util.*;
import org.springframework.data.domain.*;

/**
 * Created with IntelliJ IDEA.
 * Entiteit: ck
 * Date: 20.03.14
 * Time: 12:05
 * To change this template use File | Settings | File Templates.
 */
public class PageRequestHelper {

    //public static final String INPUT_PARAM_LIMIT = "limit";
    //public static final String INPUT_PARAM_PAGE = "page";
    public static final Integer DEFAULT_PAGE_SIZE = 25;

    /**
     * this method takes a map of key-values and extracts the "page" and "limit" values, and removes them from the
     * list,
     * if no values are found, the default values, page=0 , limit=25
     *
     * @param searchkeyvalues the searchkeyvalues
     * @return the page request from parameters
     */
    public static Pageable getPageRequestFromParameters(Map<String, String> searchkeyvalues) {

        Integer page = 0;
        Integer limit = DEFAULT_PAGE_SIZE;
        if (null != searchkeyvalues.get(SearchQuery.PAGE)) {
            page = Integer.parseInt(searchkeyvalues.get(SearchQuery.PAGE));
            searchkeyvalues.remove(SearchQuery.PAGE);
        }
        if (null != searchkeyvalues.get(SearchQuery.LIMIT)) {
            limit = Integer.parseInt(searchkeyvalues.get(SearchQuery.LIMIT));
            searchkeyvalues.remove(SearchQuery.LIMIT);
        }
        // Remove the VIEW param "mode" from the request params
        searchkeyvalues.remove("mode");
        return new org.springframework.data.domain.PageRequest(page, limit);
    }


}
