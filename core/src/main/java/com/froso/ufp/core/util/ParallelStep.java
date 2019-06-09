package com.froso.ufp.core.util;

import java.util.*;

/**
 * Created by alex on 25.02.14.
 */
public interface ParallelStep {

    /**
     * Execute boolean.
     *
     * @param index the index
     * @param list  the list
     * @return the boolean
     */
    boolean execute(int index,
                    List<?> list);
}
