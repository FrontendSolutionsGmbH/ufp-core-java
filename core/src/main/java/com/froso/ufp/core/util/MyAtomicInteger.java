package com.froso.ufp.core.util;

import java.util.concurrent.atomic.*;

/**
 * Created by ckleinhuix on 30.05.2015.
 */
public class MyAtomicInteger extends AtomicInteger {


    private static final long serialVersionUID = -5144403148018837286L;

    public MyAtomicInteger(String var1) {

        super(Integer.parseInt(var1));

    }

    public MyAtomicInteger(int var1) {

        super(var1);

    }

    public MyAtomicInteger(Integer var1) {

        super(var1);

    }

    public MyAtomicInteger() {

        super();

    }


}
