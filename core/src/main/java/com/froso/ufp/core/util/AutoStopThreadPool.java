package com.froso.ufp.core.util;
/**
 * Created by alex on 02.02.14.
 */

import java.util.*;
import java.util.concurrent.*;
import org.slf4j.*;

public class AutoStopThreadPool {

    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoStopThreadPool.class);
    private final String threadNamePrefix;
    private final int workerThreadCount;
    /**
     * Tasks which have not been started yet
     */
    private final Queue<Runnable> notStartedTaskQueue = new LinkedList<>();
    /**
     * Initial number of permits is (1-threadCount); each thread calls release() on completion; exeute() waits for
     * acquire()
     */
    private Semaphore completedSemaphore;
    /**
     * Counts the number of tasks which are either in notStartedTaskQueue OR are currently being executed
     */
    private int notCompletedTaskCount;
    /**
     * If not null, then a task has thrown an exception which should be rethrown by execute()
     */
    private RuntimeException thrown;

    /**
     * Constructor Auto stop thread pool.
     *
     * @param name              the name
     * @param workerThreadCount the worker thread count
     */
    public AutoStopThreadPool(String name,
                              int workerThreadCount) {

        this.threadNamePrefix = name;
        this.workerThreadCount = workerThreadCount;
    }

    /**
     * Add this runnable to the queue of tasks to be executed. It will only start executing when the
     * <code>execute</code> method is called.
     *
     * @param r the r
     */
    public synchronized void addTask(Runnable r) {

        notStartedTaskQueue.add(r);
        notCompletedTaskCount++;
    }

    /**
     * Starts the threads and execute all tasks, returning once they have all been completed.
     *
     * @throws RuntimeException if a task has thrown an exception. (Regrettably checked exceptions cannot be safely or
     *                          usefully thrown, <a href="http://www.databasesandlife
     *                          .com/checked-exceptions-and-java-callables/"
     *                          target=blank>more de</a>)
     */
    public void execute() {

        completedSemaphore = new Semaphore(1 - workerThreadCount);
        for (int i = 0; i < workerThreadCount; i++) {
            new Thread(new Worker(), threadNamePrefix + "-" + i).start();
        }
        completedSemaphore.acquireUninterruptibly();
        if (null != thrown) {
            throw thrown;
        }
    }

    private class Worker implements Runnable {
        /**
         * Run void.
         */
        @Override
        public void run() {

            try {
                while (true) {
                    Runnable nextTask;
                    synchronized (AutoStopThreadPool.this) {
                        // null if queue is empty
                        nextTask = notStartedTaskQueue.poll();
                    }
                    // If queue is empty then we can't start a new task;
                    //   If no task is running then no new tasks will ever be produced so shut down
                    //   If some tasks are sill running, they might produce new tasks so just wait and try again
                    if (null == nextTask) {
                        synchronized (AutoStopThreadPool.this) {
                            if (0 == notCompletedTaskCount) {
                                break;
                            }
                        }
                        try {
                            // 0.1 s
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            LOGGER.debug(e.getMessage(), e);
                        }
                        continue;
                    }
                    try {
                        nextTask.run();
                    } catch (RuntimeException e) {
                        LOGGER.debug(e.getMessage(), e);
                        thrown = e;
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage(), e);
                        thrown = new RuntimeException(e);
                    } finally {
                        synchronized (AutoStopThreadPool.this) {
                            notCompletedTaskCount--;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.debug(e.getMessage(), e);
                thrown = new RuntimeException(e);
                // Throwable ends while loop, e.g. out of memory
            } finally {
                completedSemaphore.release();
            }
        }
    }
}
