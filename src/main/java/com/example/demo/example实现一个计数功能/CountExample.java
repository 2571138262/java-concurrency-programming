package com.example.demo.example实现一个计数功能;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class CountExample {

    // 每次最多200个请求
    private static int threadTotal = 1;

    // 一共5000次请求
    private static int clientTotal = 5000;
    
    private static long count = 0;
    
    public static void main(String[] args){
        
        ExecutorService exec = Executors.newCachedThreadPool();

        /**
         * 信号量
         */
        // 同步关键类，构造方法传入的数字是多少，则同一个时刻，只运行多少个进程同时运行制定代码 
        final Semaphore semaphore = new Semaphore(threadTotal); 
        for(int index = 0; index < clientTotal; index ++){
            exec.execute(() -> {
                try{
                    /**
                     * 在 semaphore.acquire() 和 semaphore.release()之间的代码，同一时刻只允许制定个数的线程进入，
                     * 因为semaphore的构造方法是200，则同一时刻只允许200个线程进入，其他线程等待。
                     */
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("==========================count: {}", count);
    }
    
    public static void add(){
        count ++; 
    }
    
    
}
