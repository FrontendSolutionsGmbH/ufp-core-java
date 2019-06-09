package com.froso.ufp.core.domain.documents.simple.plain;

import com.froso.ufp.core.domain.interfaces.*;
import org.springframework.data.domain.*;

import java.util.*;

public class DataObjectList<T extends IDataObject> implements IDataObjectList<T> {

    private static final long serialVersionUID = -3312327733088418183L;
    private ListHeader listHeader;
    private List<T> list;

    public DataObjectList(List<T> inputList) {

        list = inputList;
        listHeader = new ListHeader();
        listHeader.setListSize(list.size());
        if (list.isEmpty()) {
            listHeader.setPaging(new PageRequest(0, 1));
        } else {
            listHeader.setPaging(new PageRequest(0, list.size()));
        }
    }

    public DataObjectList() {

        list = new ArrayList<>();
        listHeader = new ListHeader();
        listHeader.setListSize(list.size());
        if (list.isEmpty()) {
            listHeader.setPaging(new PageRequest(0, 1));
        } else {
            listHeader.setPaging(new PageRequest(0, list.size()));
        }
    }

    public DataObjectList(List<T> inputList,
                          PageRequest page,
                          long completeSize) {

        list = inputList;
        listHeader = new ListHeader();
        listHeader.setListSize(completeSize);
        listHeader.setPaging(page);
    }

    @Override
    public void addItem(T item) {

        this.list.add(item);
        listHeader.setListSize(listHeader.getListSize() + 1);
    }

    @Override
    public void addAll(List<T> item) {

        this.list.addAll(item);
        listHeader.setListSize(listHeader.getListSize() + item.size());
    }

    @Override
    public List<T> getList() {

        return list;
    }

    @Override
    public void setList(List<T> listIn) {

        list = listIn;
    }

    @Override
    public ListHeader getListHeader() {

        return listHeader;
    }

    @Override
    public void setListHeader(ListHeader listHeader) {

        this.listHeader = listHeader;
    }
}
