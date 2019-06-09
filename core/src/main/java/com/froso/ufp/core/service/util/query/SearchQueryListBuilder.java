package com.froso.ufp.core.service.util.query;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Entiteit: ck Date: 20.03.14 Time: 12:06 To change this template use File | Settings | File
 * Templates.
 */
public class SearchQueryListBuilder {
    private SearchQueryList searchQueryList = new SearchQueryList();

    public static SearchQueryListBuilder create(Map<String, String> search) {
        SearchQueryListBuilder result = new SearchQueryListBuilder();
        SearchQueryListEntry entry = new SearchQueryListEntry();
        entry.setSearch(search);

        result.searchQueryList.getSearches().add(entry);
        return result;

    }

    public SearchQueryList getSearchQueryList() {
        return searchQueryList;
    }

    public void setSearchQueryList(SearchQueryList searchQueryList) {
        this.searchQueryList = searchQueryList;
    }

    public SearchQueryListBuilder and(Map<String, String> search) {
        SearchQueryListEntry entry = new SearchQueryListEntry();
        entry.setSearch(search);
        entry.setQueryOperator(QueryOperator.AND);
        searchQueryList.getSearches().add(entry);

        return this;
    }

    public SearchQueryListBuilder or(Map<String, String> search) {
        SearchQueryListEntry entry = new SearchQueryListEntry();
        entry.setSearch(search);
        entry.setQueryOperator(QueryOperator.OR);
        searchQueryList.getSearches().add(entry);

        return this;
    }

    public SearchQueryListBuilder orNot(Map<String, String> search) {
        SearchQueryListEntry entry = new SearchQueryListEntry();
        entry.setSearch(search);
        entry.setQueryOperator(QueryOperator.ORNOT);
        searchQueryList.getSearches().add(entry);

        return this;
    }

    public SearchQueryListBuilder andNot(Map<String, String> search) {
        SearchQueryListEntry entry = new SearchQueryListEntry();
        entry.setSearch(search);
        entry.setQueryOperator(QueryOperator.ANDNOT);
        searchQueryList.getSearches().add(entry);

        return this;
    }


    public SearchQueryList build() {
        return searchQueryList;
    }

}
