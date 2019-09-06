package com.froso.ufp.modules.core.user.model;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.roles.model.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.builder.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 27.11.13 Time: 10:56 To change this
 * template use File | Settings | File Templates.
 */
@Document(collection = CoreUser.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@UFPResourceMetadataAnnotation(
        keywords =
        @ResourceKeywords({
                @ResourceKeyword("MENU_USERMANAGMENT"),
        }),
        defaultView = @ResourceViewAnnotation(
                visibleColumns = @ResourceVisibleColumns({
                        @ResourceVisibleColumn("_select"),
                        @ResourceVisibleColumn("_edit"),
                        @ResourceVisibleColumn("active"),
                        @ResourceVisibleColumn("blocked"),
                        @ResourceVisibleColumn("role"),
                        @ResourceVisibleColumn("_foreign")
                }))

)

public class CoreUser
        extends AbstractDataDocumentWithClientLink implements ICoreUser {
    public static final String TYPE_NAME = "CoreUser";
    private static final long serialVersionUID = 1;

    private CounterConsumer counters = new CounterConsumer();

    @TextIndexed
    private String firstName;
    @TextIndexed
    private String lastName;
    @ApiModelProperty(hidden = false)
    private Boolean active = Boolean.FALSE;
    @ApiModelProperty(hidden = false)
    private Boolean blocked = Boolean.FALSE;
//    @TextIndexed
//    private UserRoleEnum role = UserRoleEnum.ROLE_GUEST;
    private Set<DataDocumentLink<UserRole>> roles = new HashSet<DataDocumentLink<UserRole>>();

    public CoreUser() {
        super(TYPE_NAME);
    }

    @Override
    public Set<DataDocumentLink<UserRole>> getRoles() {
        return roles;
    }

    public void setRoles(Set<DataDocumentLink<UserRole>> roles) {
        this.roles = roles;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public CounterConsumer getCounters() {
        return counters;
    }

    public void setCounters(CounterConsumer counters) {
        this.counters = counters;
    }

    @Override
    public Boolean getBlocked() {

        return blocked;
    }

    public void setBlocked(Boolean blocked) {

        this.blocked = blocked;
    }

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
        CoreUser rhs = (CoreUser) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(active, rhs.active)
                .append(blocked, rhs.blocked)
                .isEquals();
    }
}
