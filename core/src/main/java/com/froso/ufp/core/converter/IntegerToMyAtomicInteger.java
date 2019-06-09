package com.froso.ufp.core.converter;

import com.froso.ufp.core.util.*;
import org.springframework.core.convert.converter.*;

public final class IntegerToMyAtomicInteger implements Converter<Integer, MyAtomicInteger> {

    public MyAtomicInteger convert(Integer source) {
        return new MyAtomicInteger(source);
    }

}