package com.froso.ufp.core.service.util.query;

import java.util.*;

/**
 * Created by ck on 26.09.2016.
 */
public class SearchQueryListEntry {

    private Map<String, String> search = new HashMap<>();
    private QueryOperator queryOperator;

    public Map<String, String> getSearch() {
        return search;
    }

    public void setSearch(Map<String, String> search) {
        this.search = search;
    }

    public QueryOperator getQueryOperator() {
        return queryOperator;
    }

    public void setQueryOperator(QueryOperator queryOperator) {
        this.queryOperator = queryOperator;
    }
}
