package com.example.demo.atomicexample原子性包.actimic;

import com.example.demo.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@ThreadSafe
public class LongAdderExample {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发的线程数
    public static int threadTotal = 200;

    // 累加次数 AtomicLong JDK1.8
    public static LongAdder count = new LongAdder();

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
        // count ++
//        count.getAndIncrement();
        // ++ count
        count.increment();
    }

}
