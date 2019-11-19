package com.example.demo.lockJUC锁;

import com.example.demo.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@ThreadSafe
public class LockExampleWithReentrantLock {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发的线程数
    public static int threadTotal = 200;

    // 累加次数
    public static int count = 0;
    
    private final static Lock lock = new ReentrantLock();

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
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }
        
    }

}
