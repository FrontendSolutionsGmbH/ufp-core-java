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
    /**
     * Instantiates a new Simple session.
     */
    public Session() {
        super(TYPE_NAME);
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
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

    /**
     * Gets user link.
     *
     * @return the user link
     */
    public DataDocumentLink<ICoreUser> getUserLink() {
        return userLink;
    }

    /**
     * Sets user link.
     *
     * @param userLink the user link
     */
    public void setUserLink(DataDocumentLink<ICoreUser> userLink) {
        this.userLink = userLink;
    }


    /**
     * Gets rsa server private key.
     *
     * @return the rsa server private key
     */
    @JsonIgnore
    public String getRsaServerPrivateKey() {
        return rsaServerPrivateKey;
    }

    /**
     * Sets rsa server private key.
     *
     * @param rsaServerPrivateKey the rsa server private key
     */
    @JsonProperty
    public void setRsaServerPrivateKey(String rsaServerPrivateKey) {
        this.rsaServerPrivateKey = rsaServerPrivateKey;
    }

    /**
     * Gets rsa server public key.
     *
     * @return the rsa server public key
     */
    @JsonIgnore
    public String getRsaServerPublicKey() {
        return rsaServerPublicKey;
    }

    /**
     * Sets rsa server public key.
     *
     * @param rsaServerPublicKey the rsa server public key
     */
    @JsonProperty
    public void setRsaServerPublicKey(String rsaServerPublicKey) {
        this.rsaServerPublicKey = rsaServerPublicKey;
    }

    /**
     * Gets rsa client public key.
     *
     * @return the rsa client public key
     */
    @JsonIgnore
    public String getRsaClientPublicKey() {
        return rsaClientPublicKey;
    }

    /**
     * Sets rsa client public key.
     *
     * @param rsaClientPublicKey the rsa client public key
     */
    @JsonProperty
    public void setRsaClientPublicKey(String rsaClientPublicKey) {
        this.rsaClientPublicKey = rsaClientPublicKey;
    }

    /**
     * Gets used request indices.
     *
     * @return the used request indices
     */
    public List<Integer> getUsedRequestIndices() {
        return usedRequestIndices;
    }

    /**
     * Sets used request indices.
     *
     * @param usedRequestIndices the used request indices
     */
    public void setUsedRequestIndices(List<Integer> usedRequestIndices) {
        this.usedRequestIndices = usedRequestIndices;
    }

    /**
     * Gets last used index.
     *
     * @return the last used index
     */
    public Integer getLastUsedIndex() {
        return lastUsedIndex;
    }

    /**
     * Sets last used index.
     *
     * @param lastUsedIndex the last used index
     */
    public void setLastUsedIndex(Integer lastUsedIndex) {
        this.lastUsedIndex = lastUsedIndex;
    }

    /**
     * Gets ends on.
     *
     * @return the ends on
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getEndsOn() {
        return endsOn;
    }

    /**
     * Sets ends on.
     *
     * @param endsOn the ends on
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setEndsOn(DateTime endsOn) {
        this.endsOn = endsOn;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Gets used.
     *
     * @return the used
     */
    public Integer getUsed() {
        return used;
    }

    /**
     * Sets used.
     *
     * @param used the used
     */
    public void setUsed(Integer used) {
        this.used = used;
    }

    /**
     * Gets creation time.
     *
     * @return the creation time
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Sets creation time.
     *
     * @param creationTime the creation time
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * Gets close time.
     *
     * @return the close time
     */
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    public DateTime getCloseTime() {
        return closeTime;
    }

    /**
     * Sets close time.
     *
     * @param closeTime the close time
     */
    @JsonDeserialize(using = JodaDateTimeDeSerializer.class)
    public void setCloseTime(DateTime closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * Gets active.
     *
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets remote host.
     *
     * @return the remote host
     */
    public String getRemoteHost() {
        return remoteHost;
    }

    /**
     * Sets remote host.
     *
     * @param remoteHost the remote host
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }
}
