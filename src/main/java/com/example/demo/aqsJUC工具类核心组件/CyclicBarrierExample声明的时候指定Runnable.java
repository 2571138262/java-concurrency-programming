package com.example.demo.aqsJUC工具类核心组件;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
public class CyclicBarrierExample声明的时候指定Runnable {

    // 定义并发计数器， 给定一个值告诉当前有多少个线程要来等待, 并且在声明的时候指定Runnable， 当线程达到这个屏障的时候优先执行这个Runnable
    private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
        log.info("callback is running");
    });
    
    public static void main(String[] args) throws InterruptedException {

        Executor exec = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            exec.execute(() -> {
                try {
                    race(threadNum);
                }catch (Exception e){
                    log.error("exception", e);
                }
            });
        }
        
    }
    
    public static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        barrier.await();
        log.info("{} continue", threadNum);
    }
    
}
