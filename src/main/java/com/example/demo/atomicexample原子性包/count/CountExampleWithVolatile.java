package com.example.demo.atomicexample原子性包.count;

import com.example.demo.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class CountExampleWithVolatile {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发的线程数
    public static int threadTotal = 200;

    // 累加次数
    public static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        
        // 信号量
        final Semaphore semaphore = new Semaphore(threadTotal);
        
        // 计数器闭锁
        final CountDownLatch countDownLatch = new  CountDownLatch(clientTotal);
        
        for (int i = 0; i < clientTotal; i ++){
            exec.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        
        countDownLatch.await();
        log.info("=============================count:{}", count);
    }

    public static void add() {
        count++;
        // 1、从主内存中取得count
        // 2、+1
        // 3、从新写回主内存

        /**
         * 这里线程不安全的原因是多个线程同时将自己 +1 的结果写回到主内存
         */
    }

}
