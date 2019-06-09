package com.froso.ufp.core.converter;

import com.froso.ufp.core.util.*;
import org.springframework.core.convert.converter.*;

public final class MyAtomicIntegerToInteger implements Converter<MyAtomicInteger, Integer> {

    public Integer convert(MyAtomicInteger source) {
        return source.get();
    }

}