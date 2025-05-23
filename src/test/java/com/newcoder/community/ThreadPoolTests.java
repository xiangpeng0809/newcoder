package com.newcoder.community;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.concurrent.*;

/**
 * ClassName: ThreadPoolTests
 * Package: com.newcoder.community
 * Description:
 *
 * @author 向鹏
 * @version 1.0
 * @create 2025/5/5 16:52
 */
@SpringBootTest
@ContextConfiguration(classes = ThreadPoolTests.class)
public class ThreadPoolTests {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTests.class);

    // JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    // JDK可执行定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    // Spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    // Spring定时任务线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 1.JDK普通线程池
    @Test
    public void testExecutorService() {
        Runnable runnable = new Runnable() {
            public void run() {
                logger.debug("Hello ExecutorService");
            }
        };

        for (int i = 0; i < 10; i++) {
            executorService.submit(runnable);
        }

        sleep(10000);
    }

    // 2.JDK定时任务线程池
    @Test
    public void testScheduledExecutorService() {
        Runnable runnable = new Runnable() {
            public void run() {
                logger.debug("Hello ScheduledExecutorService");
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(runnable,10000, 1000, TimeUnit.MILLISECONDS);

        sleep(30000);
    }

    // 3.Spring普通线程池
    @Test
    public void ThreadPoolTaskExecutor() {
        Runnable task = () -> System.out.println("Hello ThreadPoolTaskExecutor");
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.submit(task);
        }
    }

    // 4.Spring定时任务线程池
    @Test
    public void testThreadPoolTaskScheduler() {
        Runnable runnable = new Runnable() {
            public void run() {
                logger.debug("Hello testThreadPoolTaskScheduler");
            }
        };

        Date startTime = new Date(System.currentTimeMillis() + 1000);
        threadPoolTaskScheduler.scheduleAtFixedRate(runnable,startTime,1000);

        sleep(30000);
    }

}
