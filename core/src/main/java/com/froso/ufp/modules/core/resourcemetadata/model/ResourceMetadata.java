package com.froso.ufp.modules.core.resourcemetadata.model;

import com.froso.ufp.core.service.util.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.index.*;

import java.util.*;

@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),

        }), defaultView = @ResourceViewAnnotation(
        visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("id"),
                @ResourceVisibleColumn("resourceName")
        })
)

)
public class ResourceMetadata {

    public static final String TYPE_NAME = "ResourceMetadata";

    @Indexed(unique = true)
    private String resourceName;
    private String resourceIcon;
    private Set<String> tags = new HashSet<>();
    private Set<String> interfaces = new HashSet<>();
    private ResourceView defaultView = new ResourceView();
    private List<ResourceView> views = new ArrayList<>();
    private List<ForeignKey> foreignKeys = new ArrayList<>();
    private List<ForeignKey> foreignKeysIncoming = new ArrayList<>();

    public static String getTypeName() {
        return TYPE_NAME;

    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    public Set<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Set<String> interfaces) {
        this.interfaces = interfaces;
    }

    public ResourceView getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(ResourceView defaultView) {
        this.defaultView = defaultView;
    }

    public List<ForeignKey> getForeignKeysIncoming() {
        return foreignKeysIncoming;
    }

    public void setForeignKeysIncoming(List<ForeignKey> foreignKeysIncoming) {
        this.foreignKeysIncoming = foreignKeysIncoming;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(List<ForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public List<ResourceView> getViews() {
        return views;
    }

    public void setViews(List<ResourceView> view) {
        this.views = view;
    }
}
