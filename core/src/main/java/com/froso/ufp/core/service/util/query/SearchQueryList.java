package com.froso.ufp.core.service.util.query;

import java.util.*;

/**
 * Created by ck on 26.09.2016.
 */
public class SearchQueryList {
    private List<SearchQueryListEntry> searches = new ArrayList<>();

    public List<SearchQueryListEntry> getSearches() {
        return searches;
    }

    public void setSearches(List<SearchQueryListEntry> searches) {
        this.searches = searches;
    }
}
