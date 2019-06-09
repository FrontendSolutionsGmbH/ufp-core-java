package com.froso.ufp.modules.optional.textcomponent.model.textcomponent;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

@CompoundIndexes({
        @CompoundIndex(
                def = "{'language':1,'client.id':1}", name = "client language", unique = true, dropDups = true, background = true)})

@Document(collection = TextComponentLanguage.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "The TextComponent holds the keys for TextComponentValues, its main purpose is to provide INTL keys for internationalisation.")
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("INTL"),
                @ResourceKeyword("MENU_INTL")

        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name"),
                        @ResourceVisibleColumn("lang"),
                }))

)
public class TextComponentLanguage extends AbstractTextComponentDocument {
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String DEFAULT_LANGUAGE_NAME = "English";
    public static final String TYPE_NAME = "TextComponentLanguage";
    private static final long serialVersionUID = -5651846486759106273L;

    @Indexed(unique = true)
    @ApiParam(example = TextComponentLanguage.DEFAULT_LANGUAGE)
    @NotNull
    @NotEmpty
    @TextIndexed
    private String languageName;
    @Indexed(unique = true)
    @ApiParam(example = TextComponentLanguage.DEFAULT_LANGUAGE)
    @NotNull
    @NotEmpty
    @TextIndexed
    private String languageKey;

    public TextComponentLanguage() {
        super(TYPE_NAME);
    }

    public String getName() {
        return toString();
    }

    public String toString() {
        return getLanguageKey() + ":" + getLanguageName();
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageKey() {
        return languageKey;
    }

    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
    }
}
