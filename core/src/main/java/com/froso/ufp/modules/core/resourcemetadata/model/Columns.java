package com.froso.ufp.modules.core.resourcemetadata.model;

import java.util.*;

/**
 * Created by ckleinhuix on 01/11/2017.
 */
public class Columns {

    private List<String> fixed = new ArrayList<>();
    private List<String> visible = new ArrayList<>();
    private List<String> primary = new ArrayList<>();
    private List<String> secondary = new ArrayList<>();
    private List<String> optional = new ArrayList<>();

    public List<String> getFixed() {
        return fixed;
    }

    public void setFixed(List<String> fixed) {
        this.fixed = fixed;
    }

    public List<String> getVisible() {
        return visible;
    }

    public void setVisible(List<String> visible) {
        this.visible = visible;
    }

    public List<String> getPrimary() {
        return primary;
    }

    public void setPrimary(List<String> primary) {
        this.primary = primary;
    }

    public List<String> getSecondary() {
        return secondary;
    }

    public void setSecondary(List<String> secondary) {
        this.secondary = secondary;
    }

    public List<String> getOptional() {
        return optional;
    }

    public void setOptional(List<String> optional) {
        this.optional = optional;
    }
}
