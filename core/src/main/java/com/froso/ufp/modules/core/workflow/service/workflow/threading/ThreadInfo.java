package com.froso.ufp.modules.core.workflow.service.workflow.threading;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * Created by ckleinhuix on 22.03.2015.
 */

public class ThreadInfo implements IDataObject {

    private String threadNamePrefix;
    private int activeCount;
    private int poolSize;
    private int maxPoolSize;

    /**
     * Gets thread name prefix.
     *
     * @return the thread name prefix
     */
    public String getThreadNamePrefix() {

        return threadNamePrefix;
    }

    /**
     * Sets thread name prefix.
     *
     * @param threadNamePrefix the thread name prefix
     */
    public void setThreadNamePrefix(final String threadNamePrefix) {

        this.threadNamePrefix = threadNamePrefix;
    }

    /**
     * Gets active count.
     *
     * @return the active count
     */
    public int getActiveCount() {

        return activeCount;
    }

    /**
     * Sets active count.
     *
     * @param activeCount the active count
     */
    public void setActiveCount(final int activeCount) {

        this.activeCount = activeCount;
    }

    /**
     * Gets pool size.
     *
     * @return the pool size
     */
    public int getPoolSize() {

        return poolSize;
    }

    /**
     * Sets pool size.
     *
     * @param poolSize the pool size
     */
    public void setPoolSize(final int poolSize) {

        this.poolSize = poolSize;
    }

    /**
     * Gets max pool size.
     *
     * @return the max pool size
     */
    public int getMaxPoolSize() {

        return maxPoolSize;
    }

    /**
     * Sets max pool size.
     *
     * @param maxPoolSize the max pool size
     */
    public void setMaxPoolSize(final int maxPoolSize) {

        this.maxPoolSize = maxPoolSize;
    }
}
