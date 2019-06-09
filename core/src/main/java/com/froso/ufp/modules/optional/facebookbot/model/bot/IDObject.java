package com.froso.ufp.modules.optional.facebookbot.model.bot;

/**
 * Created by ckleinhuix on 13/08/2016.
 */
public class IDObject {
    private String id;

    /**
     * Construct id object.
     *
     * @param id the id
     * @return the id object
     */
    public static final IDObject construct(String id) {

        IDObject result = new IDObject();
        result.setId(id);
        return result;

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

}
