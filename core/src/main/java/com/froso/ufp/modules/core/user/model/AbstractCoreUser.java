package com.froso.ufp.modules.core.user.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.roles.model.*;
import io.swagger.annotations.*;
import java.util.*;
import org.apache.commons.lang3.builder.*;
import org.springframework.data.mongodb.core.index.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 10:56 To change this
 * template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("USERMANAGMENT"),
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("active"),
                        @ResourceVisibleColumn("blocked"),
                        @ResourceVisibleColumn("role"),
                        @ResourceVisibleColumn("_foreign")
                }))

)

public abstract class AbstractCoreUser
        extends AbstractDataDocumentWithClientLink implements ICoreUser {

    private CounterConsumer counters = new CounterConsumer();
    private Set<DataDocumentLink<RoleDefinition>> roles = new HashSet<DataDocumentLink<RoleDefinition>>();
    @ApiModelProperty(hidden = false, example = "true")
    private Boolean active = Boolean.TRUE;
    @ApiModelProperty(hidden = false, example = "false")
    private Boolean blocked = Boolean.FALSE;
    @TextIndexed
    @ApiModelProperty(hidden = false, example = "ROLE_ADMIN")
    private UserRoleEnum role = UserRoleEnum.ROLE_GUEST;

    /**
     * Constructor Simple user.
     *
     * @param name the name
     */
    public AbstractCoreUser(String name) {
        super(name);
    }

    /**
     * The constant TYPE_NAME.
     */
    // public static final String TYPE_NAME = "CoreUser";
    public Set<DataDocumentLink<RoleDefinition>> getRoles() {
        return roles;
    }

    /**
     * Sets roles.
     *
     * @param roles the roles
     */
    public void setRoles(Set<DataDocumentLink<RoleDefinition>> roles) {
        this.roles = roles;
    }


    /**
     * Gets counters.
     *
     * @return the counters
     */
    @Override
    public CounterConsumer getCounters() {
        return counters;
    }

    /**
     * Sets counters.
     *
     * @param counters the counters
     */
    public void setCounters(CounterConsumer counters) {
        this.counters = counters;
    }

    /**
     * Gets blocked.
     *
     * @return the blocked
     */
    @Override
    public Boolean getBlocked() {

        return blocked;
    }

    /**
     * Sets blocked.
     *
     * @param blocked the blocked
     */
    public void setBlocked(Boolean blocked) {

        this.blocked = blocked;
    }


    /**
     * Gets active.
     *
     * @return the active
     */
    @Override
    public Boolean getActive() {

        return active;
    }

    /*
       the "setActive" property shall in no cases be written by any outside source, e.g. when creating a user instance
        */

    /**
     * Sets active.
     *
     * @param active the active
     */
/*
    the "setActive" property shall in no cases be written by any outside source, e.g. when creating a user instance
     */
    public void setActive(Boolean active) {

        this.active = active;
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(HASH_CODE_ODD_NUMBER_START, HASH_CODE_ODD_NUMBER_MULTIPLIER).

                append(active).
                append(blocked).
                appendSuper(super.hashCode()).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AbstractCoreUser rhs = (AbstractCoreUser) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(active, rhs.active)
                .append(blocked, rhs.blocked)
                .isEquals();
    }
}
