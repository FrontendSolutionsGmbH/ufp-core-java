package com.froso.ufp.modules.core.templatesv2.model;

public class TemplateSettings {

    private String templatePath = null;
    private String layoutMain = null;
    private String layoutMainContentVariable = "content";
    private String languageFileName = null;
    private String useLayoutMain = null;

    /**
     * Instantiates a new Template settings.
     */
    public TemplateSettings() {

    }

    /**
     * Instantiates a new Template settings.
     *
     * @param templatePath              the template path
     * @param layoutMain                the layout main
     * @param layoutMainContentVariable the layout main content variable
     * @param languageFileName          the language file name
     * @param useLayoutMain             the use layout main
     */
    public TemplateSettings(String templatePath, String layoutMain, String layoutMainContentVariable, String languageFileName, String useLayoutMain) {
        this.useLayoutMain = useLayoutMain;
        this.templatePath = templatePath;
        this.layoutMain = layoutMain;
        this.languageFileName = languageFileName;
        this.layoutMainContentVariable = layoutMainContentVariable;
    }

    public String getUseLayoutMain() {
        return useLayoutMain;
    }

    public void setUseLayoutMain(String useLayoutMain) {
        this.useLayoutMain = useLayoutMain;
    }

    /**
     * Gets template path.
     *
     * @return the template path
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * Sets template path.
     *
     * @param templatePath the template path
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * Gets layout main.
     *
     * @return the layout main
     */
    public String getLayoutMain() {
        return layoutMain;
    }

    /**
     * Sets layout main.
     *
     * @param layoutMain the layout main
     */
    public void setLayoutMain(String layoutMain) {
        this.layoutMain = layoutMain;
    }

    /**
     * Gets layout main content variable.
     *
     * @return the layout main content variable
     */
    public String getLayoutMainContentVariable() {
        return layoutMainContentVariable;
    }

    /**
     * Sets layout main content variable.
     *
     * @param layoutMainContentVariable the layout main content variable
     */
    public void setLayoutMainContentVariable(String layoutMainContentVariable) {
        this.layoutMainContentVariable = layoutMainContentVariable;
    }

    /**
     * Gets language file name.
     *
     * @return the language file name
     */
    public String getLanguageFileName() {
        return languageFileName;
    }

    /**
     * Sets language file name.
     *
     * @param languageFileName the language file name
     */
    public void setLanguageFileName(String languageFileName) {
        this.languageFileName = languageFileName;
    }
}
