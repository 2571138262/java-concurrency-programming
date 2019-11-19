package com.example.demo.commonUnSafe常见的线程不安全;

import com.example.demo.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 堆栈封闭 ：局部变量，无并发问题
 * 将SimpleDateFormat从全局变量编程局部变量， 多个线程访问一个局部变量的时候，都会将这个变量拷贝到自己的工作内存当中
 */
@Slf4j
@ThreadSafe
public class SimpleDateFormatExample正确写法 {

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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            simpleDateFormat.parse("20191113");   
        }catch (Exception e){
            log.error("parse exception", e);
            e.printStackTrace();
        }
    }
}
