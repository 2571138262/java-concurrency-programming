package com.example.demo.lockJUC锁;

import com.example.demo.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 是一种悲观的读取方式
 * 通过ReentrantReadWriteLock实现一个数据结构
 *      这个数据结构内部封装一个Map，但是不能将Map原有的方法暴露出去
 *      针对提供一些方法，并且进行加锁操作，放在并发状态出现问题
 */
@Slf4j
@ThreadSafe
public class LockExample了解ReentrantReadWriteLock {
    
    private final Map<String, Data> map = new TreeMap<>();

    /**
     * 声明ReentrantReadWriteLock
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 声明读锁
     */
    private final Lock readLock = lock.readLock();

    /**
     * 声明写锁
     */
    private final Lock writeLock = lock.writeLock();

    /**
     * 通过key获取Data
     * @param key
     * @return
     */
    private Data get(String key){
     readLock.lock();
     try{
         return map.get(key);
     }finally {
         readLock.unlock();
     }
    }

    /**
     * 获取所有的keys
     * @return
     */
    public Set<String> getAllKeys(){
        readLock.lock();
        try{
            return map.keySet();
        }finally {
            readLock.unlock();
        }
    }

    /**
     * 添加一条记录
     * @param key
     * @param value
     * @return
     */
    public Data put(String key, Data value){
        writeLock.lock();
        try {
            return map.put(key, value);
        }finally {
            writeLock.unlock();
        }
    }
    
    class Data{
        
    }

}
