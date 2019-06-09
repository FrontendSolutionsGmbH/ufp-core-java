package com.froso.ufp.core.domain.documents;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.*;
import org.springframework.data.mongodb.core.index.*;

import javax.validation.constraints.*;
import java.io.*;
import java.util.*;

/**
 * The DataDocumentLink serves as a foreign key reference, it comes with an resourcename field, to identify the resourcename
 * for the link. And it provides a 'data' typed field to store an complete instance of the linked resource
 */
public class DataDocumentLink<T> implements IDataDocumentLink<T>, Serializable {
    private static final long serialVersionUID = 6230043267457712579L;
    @Indexed(background = true)
    @NotNull
    @ApiModelProperty(hidden = false,
            required = false,
            dataType = "string",
            example = "9703bd50-57a4-4776-8ded-f0458bfe2a6d",
            notes = "Referenced id of resource - the primary key")
    private String id = null;
    @ApiModelProperty(hidden = false, notes = "The resourcename of the referenced resource",
            readOnly = true, example = "aResourceName")
    private String resourceName;

    @ApiModelProperty(hidden = true,
            required = false,
            notes = "Referenced realised object, used to transport the data along with the link, and for persisting linked data at the link itself.")
    private T data = null;

    public DataDocumentLink() {

    }

    public DataDocumentLink(String idIn) {
        id = idIn;
    }

    public DataDocumentLink(String idIn, String resourceNameIn) {
        id = idIn;
        resourceName = resourceNameIn;
    }

    public DataDocumentLink(IDataDocumentLink idIn) {
        if (idIn != null) {
            id = idIn.getId();
        }
    }

    public DataDocumentLink(IDataDocumentLink idIn, String resourceNameIn) {
        if (idIn != null) {
            id = idIn.getId();
        }

        resourceName = resourceNameIn;
    }

    static DataDocumentLink construct(String idIn, String resourceName) {
        DataDocumentLink result = null;
        if (idIn != null) {
            result = new DataDocumentLink();

            result.setId(idIn);
            result.setResourceName(resourceName);
        }
        return result;
    }

    static DataDocumentLink construct(String idIn) {

        DataDocumentLink result = null;
        if (idIn != null) {
            result = new DataDocumentLink();
            result.setId(idIn);
        }
        return result;
    }

    static DataDocumentLink construct(DataDocumentLink data) {

        DataDocumentLink result = null;

        if (data != null) {
            result = new DataDocumentLink();
            result.setId(data.getId());
            result.setResourceName(data.getResourceName());
            result.setData(data.getData());
        }
        return result;
    }

    public static String getId(DataDocumentLink link) {
        if (null != link) {
            return link.getId();
        }
        return null;

    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        if ("".equals(id)) {
            id = null;
        }
        this.id = id;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @JsonIgnore
    public Boolean isLinked() {
        return !(id == null || "".equals(id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataDocumentLink<?> that = (DataDocumentLink<?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(resourceName, that.resourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resourceName);
    }

}
