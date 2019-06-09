package com.froso.ufp.modules.core.user.model;

import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.workflow.model.listener.*;
import java.util.*;

/**
 * Created by ckleinhuix on 05.05.2015.
 */
public class CounterHelper {

    private String name;
    private Map<String, MyAtomicInteger> countMap = new HashMap<>();

    /**
     * Instantiates a new Counter helper.
     *
     * @param nameIn the name in
     */
    public CounterHelper(String nameIn) {
        name = nameIn;

    }

    /**
     * Increase count for.
     *
     * @param name the name
     */
    public void increaseCountFor(String name) {
        if (countMap.get(name) == null) {
            // initialise count value
            countMap.put(name, new MyAtomicInteger(0));
        }

        countMap.get(name).incrementAndGet();

    }

    /**
     * Gets count for.
     *
     * @param name the name
     * @return the count for
     */
    public Integer getCountFor(String name) {
        if (countMap.get(name) == null) {
            // initialise count value
            countMap.put(name, new MyAtomicInteger(0));
        }

        return countMap.get(name).get();

    }

    /**
     * Reset count for.
     *
     * @param name the name
     */
    public void resetCountFor(String name) {
        if (countMap.get(name) == null) {
            // initialise count value
            countMap.put(name, new MyAtomicInteger(0));
        }

        countMap.get(name).set(0);

    }

    /**
     * Log counters.
     *
     * @param listenerInterface the listener interface
     */
    public void logCounters(ImportListenerInterface listenerInterface) {

        for (Map.Entry<String, MyAtomicInteger> entry : countMap.entrySet()) {
            listenerInterface.reportInfo(name + " " + entry.getKey() + " count: " + entry.getValue().get());

        }

    }

    /**
     * Gets count map.
     *
     * @return the count map
     */
    public Map<String, MyAtomicInteger> getCountMap() {
        return countMap;
    }

    /**
     * Sets count map.
     *
     * @param countMap the count map
     */
    public void setCountMap(Map<String, MyAtomicInteger> countMap) {
        this.countMap = countMap;
    }
}
