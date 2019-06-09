package com.froso.ufp.core.domain.documents.simple.plain;

import io.swagger.annotations.*;
import org.springframework.data.domain.*;

import java.io.*;

/**
 * The type List header.
 *
 * @author Christian Kleinhuis (ck@froso.de) Date: 02.11.13 Time: 12:10
 * <p>
 * The ListHeader is used to provide information about a paged list getOutput contained in an IDataObject
 * element
 */
public class ListHeader implements Serializable {

    private static final long serialVersionUID = 1;
    @ApiModelProperty(hidden = false, required = true, notes = "The total size of the list")
    private long listSize;
    @ApiModelProperty(hidden = false, required = true, notes = "Pagination setting, offset, pagesize")
    private Pageable paging;

    /**
     * listsize shall contain the absolute size of the items in the list, which is usually bigger than the returned
     * page
     * size
     *
     * @return list size
     */
    public long getListSize() {

        return listSize;
    }

    public void setListSize(long listSize) {

        this.listSize = listSize;
    }

    public Pageable getPaging() {

        return paging;
    }

    public void setPaging(Pageable paging) {

        this.paging = paging;
    }
}
