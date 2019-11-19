package com.example.demo.aqsJUC工具类核心组件;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreExample得到多个许可才可以执行的案例 {
    
    private static int theadCount = 20;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();

        // 实例化Semaphore 给定并发访问的线程数
        final Semaphore semaphore = new Semaphore(3);
        
        for (int i = 0; i < theadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try{
                    /**
                     * 下边这种写法就相当于单线程操作了， 因为同一时刻的并行线程数为3，
                     *      当前线程的信号量一次性获取了3个许可导致其他线程无法获取到许可
                     */
                    semaphore.acquire(3); // 获取多个Semaphore的许可
                    test(threadNum);
                    semaphore.release(3); // 释放多个Semaphore的许可
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
