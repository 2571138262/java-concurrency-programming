package com.example.demo.aqsJUC工具类核心组件;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CountDownLatchExample在指定时间内完成的情况 {
    
    private static int theadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        
        final CountDownLatch countDownLatch = new CountDownLatch(theadCount);

        for (int i = 0; i < theadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try{
                    test(threadNum);
                }catch (InterruptedException e){
                    log.error("exception: {}", e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        log.info("finish");
        // 关闭线程池资源
        exec.shutdown();
    }
    
    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(100);
        log.info("{}", threadNum);
    }
    
}
