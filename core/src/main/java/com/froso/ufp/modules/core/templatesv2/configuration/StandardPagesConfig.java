package com.froso.ufp.modules.core.templatesv2.configuration;

import com.froso.ufp.modules.core.templatesv1.model.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 24.03.2015.
 */
@Configuration
public class StandardPagesConfig {
    /**
     * The constant PAGE_HELP_NAME.
     */
    public static final String PAGE_HELP_NAME = "PAGE_HELP";
    /**
     * The constant PAGE_IMPRINT_NAME.
     */
    public static final String PAGE_IMPRINT_NAME = "PAGE_IMPRINT";
    /**
     * The constant PAGE_PRIVACY_NAME.
     */
    public static final String PAGE_PRIVACY_NAME = "PAGE_PRIVACY";
    /**
     * The constant PAGE_SERVICE_NAME.
     */
    public static final String PAGE_SERVICE_NAME = "PAGE_SERVICE";
    /**
     * The constant PAGE_SHIPPINGTERMS_NAME.
     */
    public static final String PAGE_SHIPPINGTERMS_NAME = "PAGE_SHIPPINGTERMS";
    /**
     * The constant BEAN_STANDARD_PAGES.
     */
    public static final String BEAN_STANDARD_PAGES = "BEAN_STANDARD_PAGES";

    /**
     * Gets standard pages.
     *
     * @return the standard pages
     */
    @Bean(name = BEAN_STANDARD_PAGES)
    public StandardPagesMap getStandardPages() {

        StandardPagesMap standardPages = new StandardPagesMap();
        standardPages.put(PAGE_HELP_NAME, "templates/pages/help.html");
        standardPages.put(PAGE_IMPRINT_NAME, "templates/pages/imprint.html");
        standardPages.put(PAGE_PRIVACY_NAME, "templates/pages/privacy.html");
        standardPages.put(PAGE_SERVICE_NAME, "templates/pages/service.html");
        standardPages.put(PAGE_SHIPPINGTERMS_NAME, "templates/pages/shippingterms.html");
        return standardPages;
    }
}
