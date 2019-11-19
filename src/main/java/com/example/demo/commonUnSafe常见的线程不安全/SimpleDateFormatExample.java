package com.example.demo.commonUnSafe常见的线程不安全;

import com.example.demo.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class SimpleDateFormatExample {

    /**
     * SimpleDateFormat 不是线程安全的对象， 多线程
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发的线程数
    public static int threadTotal = 200;

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
    }

    public static void add() {
        try {
            simpleDateFormat.parse("20191113");   
        }catch (Exception e){
            log.error("parse exception", e);
            e.printStackTrace();
        }
    }
}
