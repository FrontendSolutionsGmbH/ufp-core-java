package com.froso.ufp.modules.core.workflow.model.workflow.domain;

import java.util.*;
import org.apache.commons.lang3.*;

/**
 * Created by ckleinhuix on 12.01.2016.
 */
public class PropsManager {

    private Map<String, Object> props = new HashMap<>();
    private Map<String, Object> defaults = new HashMap<>();

    /**
     * Gets prop.
     *
     * @param path the path
     * @return the prop
     */
    public String getProp(String path) {

        return props.get(path).toString();

    }

    /**
     * Gets props.
     *
     * @return the props
     */
    public Map<String, Object> getProps() {
        return props;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }
/**
 * Load.
 *
 * @param props the props
 */


    /**
     * Sets props.
     *
     * @param path the path
     * @param root the root
     * @return the path
     */
    public Map<String, Object> getPath(String path, Map<String, Object> root) {


        String[] pathElements = path.split("\\.");
        Map<String, Object> current = root;
        if (pathElements.length == 1) {
            // return root if no path specified
            return props;

        } else {


            for (int i = 0; i < pathElements.length; i++) {
                String element = pathElements[i];
                if (i == pathElements.length - 1) {
                    // last element
                    break;
                } else if (current.get(element) != null) {

                    if (current.get(element) instanceof String) {
                        // found current
                        break;
                    } else if (current.get(element) instanceof Map) {
                        current = (Map<String, Object>) current.get(element);
                    }
                } else {
                    current.put(element, new HashMap<String, Object>());
                    current = (Map<String, Object>) current.get(element);
                }


            }


        }
        return current;
    }
    /**
     * Gets prop.
     *
     * @param pathFull     the path full
     * @param defaultValue the default value
     * @return the prop
     */

    /**
     * Gets prop.
     *
     * @param pathFull     the path full
     * @param defaultValue the default value
     * @return the prop
     */
    public String getProp(String pathFull, Object defaultValue) {
        String[] pathElements = pathFull.split("\\.");
        String path = pathElements[pathElements.length - 1];
        Map<String, Object> nodeProps = getPath(pathFull, props);
        Map<String, Object> nodeDefault = getPath(pathFull, defaults);

        if ((defaultValue != null) && (nodeDefault.get(path) == null)) {

            nodeDefault.put(path, defaultValue);

        }
        Object result = nodeProps.get(path);
        if (result == null) {
            nodeProps.put(path, defaultValue);
            result = nodeProps.get(path);
        }
        if (result != null) {
            return result.toString();
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * Sets prop.
     *
     * @param pathFull the path full
     * @param value    the value
     * @return the prop
     */
    public void setProp(String pathFull, Object value) {
        String[] pathElements = pathFull.split("\\.");
        String path = pathElements[pathElements.length - 1];
        Map<String, Object> nodeProps = getPath(pathFull, props);

        nodeProps.put(path, value);

    }

}
