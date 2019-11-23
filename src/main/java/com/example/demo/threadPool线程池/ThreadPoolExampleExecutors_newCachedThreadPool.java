package com.example.demo.threadPool线程池;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadPoolExampleExecutors_newCachedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(() -> {
                log.info("task: {}", index);
            });
        }
        
        executorService.shutdown();
    }
    
}
