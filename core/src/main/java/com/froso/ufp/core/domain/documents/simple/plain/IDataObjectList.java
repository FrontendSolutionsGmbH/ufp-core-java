package com.froso.ufp.core.domain.documents.simple.plain;

import com.froso.ufp.core.domain.interfaces.*;
import java.util.*;

/**
 * Created by ckleinhuix on 02.12.2015.
 */
public interface IDataObjectList<T extends IDataObject> extends IDataObject {
    void addItem(T item);

    void addAll(List<T> item);

    List<T> getList();

    void setList(List<T> listIn);

    ListHeader getListHeader();

    void setListHeader(ListHeader listHeader);
}
