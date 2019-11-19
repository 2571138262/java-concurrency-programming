package com.example.demo.concurrent并发容器测试包;

import com.example.demo.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {

    // 请求总数
    private static int clientTotal = 5000;

    // 同时并发的线程数
    public static int threadTotal = 200;
    
    private static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();

        // 信号量
        final Semaphore semaphore = new Semaphore(threadTotal);

        // 计数器闭锁
        final CountDownLatch countDownLatch = new  CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i ++){
            final int count = i;
            exec.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        exec.shutdown();
        log.info("size {}", list.size());
    }

    public static void update(int i) {
        list.add(i);
    }
    
}
