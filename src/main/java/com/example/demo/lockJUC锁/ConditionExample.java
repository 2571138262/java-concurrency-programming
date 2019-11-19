package com.example.demo.lockJUC锁;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 关于Condition的使用
 */
@Slf4j
public class ConditionExample {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        
        // 获取Condition
        Condition condition = reentrantLock.newCondition();
        
        new Thread(() -> {
            try{
                reentrantLock.lock();
                log.info("wait signal"); // 1
                /**
                 * 调用了condition.await();之后， 这个线程就从AQS队列中 
                 */
                condition.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            log.info("get signal"); // 4
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {
                reentrantLock.lock();
                log.info("get signal"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            log.info("send signal"); // 3
            reentrantLock.unlock();
        }).start();
        
    }
    
}
