package com.froso.ufp.core.configuration;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.concurrent.*;
//
@Configuration
public class ThreadConfig {

    public static final String PREFIX = "RETAIL-";
    public static final String PREFIX_DEFAULT = "DEFAULT";
    public static final String HIGH_PRIO_THREADPOOL = "-THREAD-POOL-HIGH-";
    public static final String LOW_PRIO_THREADPOOL = "-THREAD-POOL-LOW-";
    public static final String MEDIUM_PRIO_THREADPOOL = "-THREAD-POOL-MED-";
    public static final int CORE_POOL_SIZE = 10;
    public static final int MAX_POOL_SIZE = 25;

    /**
     * Task executor high.
     *
     * @return the thread pool task executor
     */
    @Bean(name = HIGH_PRIO_THREADPOOL)
    public ThreadPoolTaskExecutor taskExecutorHigh() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix(PREFIX + getThreadPrefix() + HIGH_PRIO_THREADPOOL);
        pool.setCorePoolSize(CORE_POOL_SIZE);
        pool.setMaxPoolSize(MAX_POOL_SIZE);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    /**
     * templateString method, override in sub controller to distinguish thread names
     *
     * @return thread prefix
     */
    public String getThreadPrefix() {

        return PREFIX_DEFAULT;
    }

    /**
     * Task executor low.
     *
     * @return the thread pool task executor
     */
    @Bean(name = LOW_PRIO_THREADPOOL)
    public ThreadPoolTaskExecutor taskExecutorLow() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix(PREFIX + getThreadPrefix() + LOW_PRIO_THREADPOOL);
        pool.setCorePoolSize(CORE_POOL_SIZE);
        pool.setMaxPoolSize(MAX_POOL_SIZE);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    /**
     * Task executor med.
     *
     * @return the thread pool task executor
     */
    @Bean(name = MEDIUM_PRIO_THREADPOOL)
    public ThreadPoolTaskExecutor taskExecutorMed() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix(PREFIX + getThreadPrefix() + MEDIUM_PRIO_THREADPOOL);
        pool.setCorePoolSize(CORE_POOL_SIZE);
        pool.setMaxPoolSize(MAX_POOL_SIZE);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
