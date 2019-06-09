package com.froso.ufp.modules.optional.textcomponent.model.textcomponent;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;
import java.util.*;

@Document(collection = TextComponent.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "The TextComponent holds the keys for TextComponentValues, its main purpose is to provide INTL keys for internationalisation.")
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("INTL"),
                @ResourceKeyword("MENU_INTL")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name"),
                        @ResourceVisibleColumn("key")
                }))

)
public class TextComponent extends AbstractTextComponentDocument {

    public static final String TYPE_NAME = "TextComponent";
    private static final long serialVersionUID = 7259332089453906794L;

    @ApiModelProperty("An entry may belong to more than one groups")
    private List<String> groupNames = new ArrayList<>();
    @Indexed(unique = true)
    @ApiModelProperty("The actual intl key")
    @NotNull
    @NotEmpty
    @TextIndexed
    private String key;

    public TextComponent() {

        super(TYPE_NAME);
    }

    public String getName() {
        return getKey();
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
