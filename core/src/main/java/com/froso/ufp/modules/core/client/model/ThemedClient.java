package com.froso.ufp.modules.core.client.model;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.modules.optional.theme.model.*;

public class ThemedClient extends Client {

    private DataDocumentLink<ThemeModel> theme;

    public DataDocumentLink<ThemeModel> getTheme() {
        return theme;
    }

    public void setTheme(DataDocumentLink<ThemeModel> theme) {
        this.theme = theme;
    }
}
