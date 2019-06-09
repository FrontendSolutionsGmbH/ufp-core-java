package com.froso.ufp.modules.optional.theme.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.optional.media.model.*;
import io.swagger.annotations.*;
import java.util.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("CONFIGURATION"), @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("MENU_CONFIGURATION"),
                @ResourceKeyword("THEME"),
                @ResourceKeyword("MENU_THEME")

        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name"),
                        @ResourceVisibleColumn("logo"),
                        @ResourceVisibleColumn("teaser")
                })
        )
)
@Document(collection = ThemeModel.TYPE_NAME)
public class ThemeModel extends AbstractDataDocumentWithClientLinkAndName {


    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "ThemeModel";

    @ApiModelProperty(position = 4, dataType = "text")
    private String css;

    private List<KeyValueItem> properties = new ArrayList<>();
    private List<KeyValueItemTyped<UfpColor>> colors = new ArrayList<>();

    @ApiModelProperty(position = -1)
    private DataDocumentLink<FilePicture> logo;
    @ApiModelProperty(position = -2)
    private DataDocumentLink<FilePicture> teaser;
    @ApiModelProperty(position = -3)
    private DataDocumentLink<FilePicture> productLogo;

    public ThemeModel() {

        super(TYPE_NAME);
    }

    /**
     * Constructor Simple app device.
     */
// Constructor
    public DataDocumentLink<FilePicture> getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(DataDocumentLink<FilePicture> productLogo) {
        this.productLogo = productLogo;
    }

    public List<KeyValueItemTyped<UfpColor>> getColors() {
        return colors;
    }

    public void setColors(List<KeyValueItemTyped<UfpColor>> colors) {
        this.colors = colors;
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public List<KeyValueItem> getProperties() {
        return properties;
    }

    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    public void setProperties(List<KeyValueItem> properties) {
        this.properties = properties;
    }

    /**
     * Gets css.
     *
     * @return the css
     */
    public String getCss() {
        return css;
    }

    /**
     * Sets css.
     *
     * @param css the css
     */
    public void setCss(String css) {
        this.css = css;
    }

    /**
     * Gets logo.
     *
     * @return the logo
     */
    public DataDocumentLink<FilePicture> getLogo() {
        return logo;
    }

    /**
     * Sets logo.
     *
     * @param logo the logo
     */
    public void setLogo(DataDocumentLink<FilePicture> logo) {
        this.logo = logo;
    }

    /**
     * Gets teaser.
     *
     * @return the teaser
     */
    public DataDocumentLink<FilePicture> getTeaser() {
        return teaser;
    }

    /**
     * Sets teaser.
     *
     * @param teaser the teaser
     */
    public void setTeaser(DataDocumentLink<FilePicture> teaser) {
        this.teaser = teaser;
    }

}
