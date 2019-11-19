package com.example.demo.atomicexample原子性包.synchronized原子性;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample修饰一个静态方法 {
    
    public static void test1(int j){
        // 修饰一个类
        synchronized (SynchronizedExample修饰一个静态方法.class) {
            for (int i = 1; i < 10; i++){
                log.info("test1 {} - {}", j, i);          
            }
        }
    }
    
    // 修饰一个静态方法
    public static synchronized void test2(int j){
        for (int i = 0; i < 10; i ++){
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample修饰一个静态方法 synchronizedExample = new SynchronizedExample修饰一个静态方法();

        SynchronizedExample修饰一个静态方法 synchronizedExample2 = new SynchronizedExample修饰一个静态方法();

        ExecutorService exec = Executors.newCachedThreadPool();
        
        exec.execute(() -> {
            synchronizedExample.test1(1);
        });

        exec.execute(() -> {
            synchronizedExample2.test1(2);
        });
    }
    
}
