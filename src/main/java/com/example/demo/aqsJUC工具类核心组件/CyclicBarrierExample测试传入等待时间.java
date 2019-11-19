package com.example.demo.aqsJUC工具类核心组件;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class CyclicBarrierExample测试传入等待时间 {

    // 定义并发计数器， 给定一个值告诉当前有多少个线程要来等待
    private static CyclicBarrier barrier = new CyclicBarrier(5);
    
    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();

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
        exec.shutdown();
    }
    
    public static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        log.info("{} is ready", threadNum);
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);
        }catch (BrokenBarrierException e){
            log.error("BrokenBarrierException", e);
        }catch (Exception e){
            log.error("Exception", e);
        }
        log.info("{} continue", threadNum);
    }
    
}
