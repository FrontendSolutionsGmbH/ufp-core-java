package com.froso.ufp.modules.core.resourcemetadata.model;

import com.froso.ufp.modules.core.resourcemetadata.annotations.*;

/**
 * Created by ckleinhuis on 23.11.2016.
 */
public class SortData {
    private String name;
    private Integer index;
    private SortMethod direction;

    public static SortData getDefaultSortData() {
        SortData result = new SortData();
        result.setName("metaData.creationTimestamp");
        result.setDirection(SortMethod.DESC);
        return result;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public SortMethod getDirection() {
        return direction;
    }

    public void setDirection(SortMethod direction) {
        this.direction = direction;
    }
}
