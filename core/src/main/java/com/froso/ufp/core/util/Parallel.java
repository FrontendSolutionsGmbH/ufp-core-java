package com.froso.ufp.core.util;

import com.froso.ufp.core.exceptions.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.slf4j.*;

/**
 * Created by alex on 25.02.14.
 */
public final class Parallel {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parallel.class);

    private Parallel() {

        throw new UtilityClassInstanciationException();
    }

    /**
     * parallelFor void.
     *
     * @param objects    the objects
     * @param maxThreads the max threads
     * @param threadName the thread name
     * @param todo       the todo
     */
    public static void parallelFor(final List<?> objects,
                                   int maxThreads,
                                   String threadName,
                                   final ParallelStep todo) {

        if (!objects.isEmpty()) {
            LOGGER.trace("Parallel parallelFor Executing Start");
            int threadCount = (objects.size() < maxThreads) ? objects.size() : maxThreads;
            AutoStopThreadPool threadPool = new AutoStopThreadPool(threadName, threadCount);
            final AtomicInteger threadIndex = new AtomicInteger(0);
            for (int i = 0; i < threadCount; i++) {
                threadPool.addTask(new Runnable() {
                    @Override
                    public void run() {

                        while (true) {
                            int myIndex = threadIndex.getAndAdd(1);
                            if (myIndex < objects.size()) {
                                try {
                                    if (!todo.execute(myIndex, objects)) {
                                        break;
                                    }
                                } catch (Exception ex) {
                                    LOGGER.warn("ParallelForWhileException " + ex.getMessage(), ex);
                                }
                            } else {
                                break;
                            }
                        }
                    }
                });
            }
            try {
                LOGGER.trace("Parallel parallelFor Executing ThreadPool");
                threadPool.execute();
                LOGGER.trace("Parallel parallelFor Executing ThreadPool Finished");
            } catch (Exception ex) {
                LOGGER.warn("ParallelForExecuteException " + ex.getMessage(), ex);
            }
        }
    }
}
