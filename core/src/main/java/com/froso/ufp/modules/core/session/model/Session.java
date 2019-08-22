package com.froso.ufp.modules.core.session.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.jsonformatters.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.user.model.*;
import io.swagger.annotations.*;

import java.util.*;

import org.apache.commons.lang3.*;
import org.joda.time.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ckleinhuix on 30.05.2015.
 */
@Document(collection = Session.TYPE_NAME)


@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_MONITORING"),
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("SESSION"),
                @ResourceKeyword("MENU_SESSION")
        }),
        defaultView = @ResourceViewAnnotation(
                sort = @ResourceFilterSortValues(
                        @ResourceFilterSortValue(value = "metaData.creationTimestamp", direction = SortMethod.DESC)
                ),
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("creationTime"),
                        @ResourceVisibleColumn("endsOn"),
                        @ResourceVisibleColumn("remoteHost"),
                        @ResourceVisibleColumn("userLink"),
                }))


)

public class Session
        extends ClientReference {


    /**
     * The constant PROP_NAME_SESSION_DURATION.
     */
    public static final String PROP_NAME_SESSION_DURATION = "security.hmac.validduration";
    /**
     * The constant TYPE_NAME.
     */
    public static final String TYPE_NAME = "Session";
    private DataDocumentLink<ICoreUser> userLink = new DataDocumentLink<>();
    private DateTime creationTime = DateTime.now();
    private DateTime closeTime;
    private DateTime endsOn;
    private Set<String> rights;
    private String firstName;
    private String lastName;
    private Boolean active = false;
    @ApiModelProperty(hidden = true)
    private String rsaServerPrivateKey = StringUtils.EMPTY;
    @ApiModelProperty(hidden = true)
    private String rsaServerPublicKey = StringUtils.EMPTY;
    @ApiModelProperty(hidden = true)
    private String rsaClientPublicKey = StringUtils.EMPTY;
    @Indexed(background = true)
    private String token;
    private Integer used = 0;
    private String remoteHost;
    private String error;
    private List<Integer> usedRequestIndices = new ArrayList<>();
    private Integer lastUsedIndex = 0;

    public Session() {
        super(TYPE_NAME);
    }

    public static String getTypeName() {
        return TYPE_NAME;
    }

    public Set<String> getRights() {
        return rights;
    }

    public void setRights(Set<String> rights) {
        this.rights = rights;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DataDocumentLink<ICoreUser> getUserLink() {
        return userLink;
    }

    public void setUserLink(DataDocumentLink<ICoreUser> userLink) {
        this.userLink = userLink;
    }

    @JsonIgnore
    public String getRsaServerPrivateKey() {
        return rsaServerPrivateKey;
    }

    @JsonProperty
    public void setRsaServerPrivateKey(String rsaServerPrivateKey) {
        this.rsaServerPrivateKey = rsaServerPrivateKey;
    }

    @JsonIgnore
    public String getRsaServerPublicKey() {
        return rsaServerPublicKey;
    }

    @JsonProperty
    public void setRsaServerPublicKey(String rsaServerPublicKey) {
        this.rsaServerPublicKey = rsaServerPublicKey;
    }

    @JsonIgnore
    public String getRsaClientPublicKey() {
        return rsaClientPublicKey;
    }

    @JsonProperty
    public void setRsaClientPublicKey(String rsaClientPublicKey) {
        this.rsaClientPublicKey = rsaClientPublicKey;
    }

    public List<Integer> getUsedRequestIndices() {
        return usedRequestIndices;
    }

    public void setUsedRequestIndices(List<Integer> usedRequestIndices) {
        this.usedRequestIndices = usedRequestIndices;
    }

    public Integer getLastUsedIndex() {
        return lastUsedIndex;
    }

    public void setLastUsedIndex(Integer lastUsedIndex) {
        this.lastUsedIndex = lastUsedIndex;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getEndsOn() {
        return endsOn;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setEndsOn(DateTime endsOn) {
        this.endsOn = endsOn;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCreationTime() {
        return creationTime;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCloseTime() {
        return closeTime;
    }

    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCloseTime(DateTime closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
}
