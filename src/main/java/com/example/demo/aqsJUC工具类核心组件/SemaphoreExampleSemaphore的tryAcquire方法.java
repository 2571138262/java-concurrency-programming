package com.example.demo.aqsJUC工具类核心组件;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SemaphoreExampleSemaphore的tryAcquire方法 {
    
    private static int theadCount = 20;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();

        // 实例化Semaphore 给定并发访问的线程数
        final Semaphore semaphore = new Semaphore(3);
        
        for (int i = 0; i < theadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try{
                    // 尝试获取一个许可
                    if (semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS)){
                        test(threadNum);
                        semaphore.release(); // 释放多个Semaphore的许可   
                    }
                }catch (InterruptedException e){
                    log.error("exception: {}", e);
                }
            });
        }
        log.info("finish");
        // 关闭线程池资源
        exec.shutdown();
    }
    
    private static void test(int threadNum) throws InterruptedException {
        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
    
}
