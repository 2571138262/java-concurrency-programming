package com.example.demo.commonUnSafe常见的线程不安全;

import com.example.demo.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class StringBufferExample {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发的线程数
    public static int threadTotal = 200;

    public static StringBuffer stringBuffer = new StringBuffer();

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
                    update();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        log.info("=============================stringBuffer的长度:{}", stringBuffer.length());
    }

    public static void update() {
        stringBuffer.append("1");
    }
    
}
