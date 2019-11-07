package com.example.demo.atomicexample原子性包.actimic;

import com.example.demo.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@ThreadSafe
public class AtomicBooleanExample {

    private static AtomicBoolean isHappened = new AtomicBoolean(false);

    // 请求总数
    public static int clientTotal = 5000;
    
    // 同时迸发执行的线程数
    public static int threadTotal = 200;

    public static void main(String[] args) throws InterruptedException {
        // 实例化线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 实例化信号量 并发执行的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        // 实例化计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i ++){
            exec.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        
        countDownLatch.await();
        exec.shutdown();
        log.info("======isHappened:{}", countDownLatch);
    }

    private static void test() {
        if (isHappened.compareAndSet(false, true)){
            log.info("execute");
        }
    }

}
