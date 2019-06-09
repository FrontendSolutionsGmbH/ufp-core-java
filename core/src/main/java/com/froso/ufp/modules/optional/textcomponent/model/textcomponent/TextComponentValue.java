package com.froso.ufp.modules.optional.textcomponent.model.textcomponent;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;

@Document(collection = TextComponentValue.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@CompoundIndexes({
        @CompoundIndex(def = "{'componentLink._id':1,'languageLink._id':1}",
                name = "identifier_component_language",
                unique = true,
                dropDups = true,
                background = true)
})
@ApiModel(description = "Concrete translation for a Language and an INTL key stored in TextComponent")

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
                )
        )
)
public class TextComponentValue extends AbstractTextComponentDocument {

    public static final String TYPE_NAME = "TextComponentValue";

    @ApiModelProperty("Link to the referred Language")
    private DataDocumentLink<TextComponentLanguage> languageLink = new DataDocumentLink<>(TextComponentLanguage.TYPE_NAME);
    @ApiModelProperty("Link to the referred TextComponent")
    private DataDocumentLink<TextComponent> componentLink = new DataDocumentLink<>(TextComponent.TYPE_NAME);

    @ApiParam(example = "Translation")
    @NotNull
    @TextIndexed
    private String value;

    public TextComponentValue() {

        super(TYPE_NAME);
    }

    public DataDocumentLink<TextComponentLanguage> getLanguageLink() {
        return languageLink;
    }

    public void setLanguageLink(DataDocumentLink<TextComponentLanguage> languageLink) {
        this.languageLink = languageLink;
    }

    public DataDocumentLink<TextComponent> getComponentLink() {
        return componentLink;
    }

    public void setComponentLink(DataDocumentLink<TextComponent> componentLink) {
        this.componentLink = componentLink;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
