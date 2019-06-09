package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.interfaces.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;

public class ClientReferenceWithName extends ClientReference implements IDataDocumentWithName {

    @TextIndexed
    @NotNull
    private String name;

    public ClientReferenceWithName(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
