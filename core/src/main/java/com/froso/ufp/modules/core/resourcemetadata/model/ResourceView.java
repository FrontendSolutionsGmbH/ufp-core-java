package com.froso.ufp.modules.core.resourcemetadata.model;

import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import java.util.*;

/**
 * Created by ckleinhuix on 08/10/2016.
 */
public class ResourceView {

    private String name;
    private Map<String, String> filter = new HashMap<>();
    // private Set<String> visibleColumns = new LinkedHashSet<>();
    private List<SortData> sorting = new ArrayList<>();
    private ViewType viewType = ViewType.FILTER;
    private Columns columns = new Columns();
    private Set<String> tags = new TreeSet<>();

    public Columns getColumns() {
        return columns;
    }

    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    /**
     * Gets view type.
     *
     * @return the view type
     */
    public ViewType getViewType() {
        return viewType;
    }

    /**
     * Sets view type.
     *
     * @param viewType the view type
     */
    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets sorting.
     *
     * @return the sorting
     */
    public List<SortData> getSorting() {
        return sorting;
    }

    /**
     * Sets sorting.
     *
     * @param sorting the sorting
     */
    public void setSorting(List<SortData> sorting) {
        this.sorting = sorting;
    }

    /**
     * Gets filter.
     *
     * @return the filter
     */
    public Map<String, String> getFilter() {
        return filter;
    }

    /**
     * Sets filter.
     *
     * @param filter the filter
     */
    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

}
