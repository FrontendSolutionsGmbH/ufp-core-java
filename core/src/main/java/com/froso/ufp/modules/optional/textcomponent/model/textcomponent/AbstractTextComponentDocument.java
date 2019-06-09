package com.froso.ufp.modules.optional.textcomponent.model.textcomponent;

import com.froso.ufp.modules.core.client.model.*;

abstract class AbstractTextComponentDocument extends ClientReference {

    private static final long serialVersionUID = 8644057981528510322L;

    AbstractTextComponentDocument(String type) {
        super(type);
    }
}
