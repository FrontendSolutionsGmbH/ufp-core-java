package com.froso.ufp.modules.optional.userresourcefilter.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.user.model.*;
import io.swagger.annotations.*;
import java.util.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * The type User resource filter model.
 */
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("STARTUPZOOM"),
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("name")
                }))


)
@Document(collection = UserResourceFilterModel.TYPE_NAME)
public class UserResourceFilterModel extends AbstractDataDocumentWithClientLinkAndName {
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "UserResourceFilter";
    private Map<String, Object> filter = new HashMap<String, Object>();
    @ApiModelProperty(notes = "The Resource this filter is dedicated for")
    private String resourceName;
    private List<DataDocumentLink<IDataDocument>> relevants = new ArrayList<>();
    private DataDocumentLink<ICoreUser> coreUser;

    /**
     * Instantiates a new Report request.
     */
    public UserResourceFilterModel
    () {
        super(TYPE_NAME);
    }

    /**
     * Gets relevants.
     *
     * @return the relevants
     */
    public List<DataDocumentLink<IDataDocument>> getRelevants() {
        return relevants;
    }

    /**
     * Sets relevants.
     *
     * @param relevants the relevants
     */
    public void setRelevants(List<DataDocumentLink<IDataDocument>> relevants) {
        this.relevants = relevants;
    }

    /**
     * Gets core user.
     *
     * @return the core user
     */
    public DataDocumentLink<ICoreUser> getCoreUser() {
        return coreUser;
    }

    /**
     * Sets core user.
     *
     * @param coreUser the core user
     */
    public void setCoreUser(DataDocumentLink<ICoreUser> coreUser) {
        this.coreUser = coreUser;
    }

    /**
     * Gets resource name.
     *
     * @return the resource name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets resource name.
     *
     * @param resourceName the resource name
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Gets filter.
     *
     * @return the filter
     */
    public Map<String, Object> getFilter() {
        return filter;
    }

    /**
     * Sets filter.
     *
     * @param filter the filter
     */
    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }
}
