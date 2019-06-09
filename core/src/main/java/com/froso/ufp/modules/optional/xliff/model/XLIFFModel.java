package com.froso.ufp.modules.optional.xliff.model;

import com.froso.ufp.core.domain.documents.AbstractDataDocumentWithName;
import com.froso.ufp.modules.optional.xliff.xsd.xliff2.*;

public class XLIFFModel extends AbstractDataDocumentWithName {

    public static final String TYPE_NAME = "XLIFF";


    private Xliff xliff = new Xliff();

    public XLIFFModel() {
        super(TYPE_NAME);

    }

    public Xliff getXliff() {
        return xliff;
    }

    public void setXliff(Xliff xliff) {
        this.xliff = xliff;
    }
}
