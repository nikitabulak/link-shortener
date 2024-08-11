package ru.bulak.linkshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.*;

@Configuration
@EnableAsync
@EnableScheduling
public class ExecutorConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService singleExecutor() {
        return new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService elasticExecutor() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10, 10,
                60L, TimeUnit.SECONDS,
                queue, new ThreadPoolExecutor.AbortPolicy());

        threadPoolExecutor.allowCoreThreadTimeOut(true);

        return threadPoolExecutor;
    }
}
