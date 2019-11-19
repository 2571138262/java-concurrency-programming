package com.example.demo.atomicexample原子性包.synchronized原子性;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample {
    
    public void test1(int j){
        // 修饰一个代码块
        synchronized (this) {
            for (int i = 1; i < 10; i++){
                log.info("test1 {} - {}", j, i);          
            }
        }
    }
    
    // 修饰一个方法
    public synchronized void test2(int j){
        for (int i = 0; i < 10; i ++){
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample synchronizedExample = new SynchronizedExample();

        SynchronizedExample synchronizedExample2 = new SynchronizedExample();

        ExecutorService exec = Executors.newCachedThreadPool();
        
        exec.execute(() -> {
            synchronizedExample.test2(1);
        });

        exec.execute(() -> {
            synchronizedExample2.test2(2);
        });
    }
    
}
