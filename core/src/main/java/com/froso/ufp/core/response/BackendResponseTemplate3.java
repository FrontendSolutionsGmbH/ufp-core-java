package com.froso.ufp.core.response;

import com.froso.ufp.core.domain.documents.simple.plain.DataObjectList;
import com.froso.ufp.core.domain.documents.simple.plain.IDataObjectList;
import com.froso.ufp.core.domain.interfaces.IDataObject;

import java.util.List;

public class BackendResponseTemplate3<T extends IDataObject, Y extends IResponseStatusTyped> extends BackendResponseTemplate3Empty<Y> {

    private IDataObjectList<T> result=new DataObjectList<>();

    BackendResponseTemplate3(Y infoInput) {
        super(infoInput);
    }


    public IDataObjectList<T> getResult() {

        return result;
    }

    public void setResult(IDataObjectList<T> result) {
        this.result = result;
    }

    public void addResult(T resultData) {

        result.addItem(resultData);
    }

    public void addResult(IDataObjectList<T> resultData) {

        result = resultData;
    }

    public void addAll(List<T> resultData) {

        result.addAll(resultData);
    }
}
