package com.froso.ufp.core.domain.documents;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.interfaces.*;
import io.swagger.annotations.*;
import org.apache.commons.lang3.builder.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

public abstract class AbstractDataDocumentWithName extends AbstractDataDocument implements INamedObject {

    private static final long serialVersionUID = 5889575270169485700L;
    @TextIndexed
    @ApiModelProperty(position = -1000)
    @NotNull
    private String name;

    protected AbstractDataDocumentWithName(String type) {

        super(type);
        name = "New " + type;
    }

    @JsonView(JacksonViews.Minimal.class)
    //  @Indexed(unique = false)

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(HASH_CODE_ODD_NUMBER_START, HASH_CODE_ODD_NUMBER_MULTIPLIER).
                append(name).
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
        AbstractDataDocumentWithName rhs = (AbstractDataDocumentWithName) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, rhs.name)
                .isEquals();
    }

}
