package com.froso.ufp.modules.core.templatesv2.model.velocity;

/**
 * Created by ck on 05.04.2016.
 */
public class PathTool {


    private String path;
    private String area;


    public PathTool(String basePath, String area) {
        this.path = basePath;
        this.area = area;

    }

    public String templatePath(String value) {

        return path + "/" + value;

    }

    public String getAreaPath(String value) {

        return path + "/" + area + "/" + value;

    }


}
