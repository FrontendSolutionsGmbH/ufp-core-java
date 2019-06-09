package com.froso.ufp.modules.core.templatesv1.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.slf4j.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by alex on 05.05.14.
 */
@Document(collection = TemplateV1.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@CompoundIndexes({@CompoundIndex(name = "CorporateAndName",
        def = "{'corporateId': 1, 'name': 1}")})

@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION")

        }),
        defaultView = @ResourceViewAnnotation(visibleColumns = @ResourceVisibleColumns({
                @ResourceVisibleColumn("id"),
                @ResourceVisibleColumn("name")
        }))

)
public class TemplateV1 extends AbstractDataDocumentWithClientLink {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "TemplateV1";
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateV1.class);
    private String contentPreview;
    private String content;
    @Indexed
    private String name;
    private String title;
    private String comment;
    private String path;
    @Indexed
    private String corporateId;
    private PageTypeEnum type;
    private PageVisibilityEnum visibility;
    private String parentId;
    private String fullPageUrl;
    private String authorID;

    /**
     * Constructor Simple template.
     */
    public TemplateV1() {

        super(TYPE_NAME);
    }

    /**
     * Gets full page url.
     *
     * @return the full page url
     */
    public String getFullPageUrl() {

        return fullPageUrl;
    }

    /**
     * Sets full page url.
     *
     * @param fullPageUrl the full page url
     */
    public void setFullPageUrl(String fullPageUrl) {

        this.fullPageUrl = fullPageUrl;
    }

    /**
     * Gets content preview.
     *
     * @return the content preview
     */
    public String getContentPreview() {

        return contentPreview;
    }

    /**
     * Sets content preview.
     *
     * @param contentPreview the content preview
     */
    public void setContentPreview(String contentPreview) {

        this.contentPreview = contentPreview;
    }

    /**
     * Gets parent id.
     *
     * @return the parent id
     */
    public String getParentId() {

        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     */
    public void setParentId(String parentId) {

        this.parentId = parentId;
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {

        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {

        this.content = content;
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
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {

        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public PageTypeEnum getType() {

        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(PageTypeEnum type) {

        this.type = type;
    }

    /**
     * Gets visibility.
     *
     * @return the visibility
     */
    public PageVisibilityEnum getVisibility() {

        return visibility;
    }

    /**
     * Sets visibility.
     *
     * @param visibility the visibility
     */
    public void setVisibility(PageVisibilityEnum visibility) {

        this.visibility = visibility;
    }

    /**
     * Gets author id.
     *
     * @return the author id
     */
    public String getAuthorID() {
        return authorID;
    }

    /**
     * Sets author id.
     *
     * @param authorID the author id
     */
    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    /**
     * Gets corporate id.
     *
     * @return the corporate id
     */
    public String getCorporateId() {

        return corporateId;
    }

    /**
     * Sets corporate id.
     *
     * @param corporateId the corporate id
     */
    public void setCorporateId(String corporateId) {

        this.corporateId = corporateId;
    }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {

        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {

        this.comment = comment;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {

        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {

        this.path = path;
    }
}
