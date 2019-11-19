package com.example.demo.aqsJUC工具类核心组件;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SemaphoreExample当前线程的并发数超过自定义的并发数的时候进行丢弃 {
    
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
//                    if (semaphore.tryAcquire()){ // 如果当前线程可以拿到许可就做，拿不到就丢弃
                    if (semaphore.tryAcquire(1, TimeUnit.SECONDS)){
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
