package com.froso.ufp.modules.core.templatesv2.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.optional.media.model.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = FileTemplate.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)

@UFPResourceMetadataAnnotation(
        keywords = @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                )
        )

)

public class FileTemplate extends UfpFile {
    public static final String TYPE_NAME = "FileTemplate";
    private static final long serialVersionUID = 1L;
    private TemplateSettings templateSettings = new TemplateSettings();

    @ApiModelProperty
    private String comment;

    public FileTemplate() {

        super(TYPE_NAME);
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment;
    }

    public TemplateSettings getTemplateSettings() {
        return templateSettings;
    }

    public void setTemplateSettings(TemplateSettings templateSettings) {
        this.templateSettings = templateSettings;
    }
}
