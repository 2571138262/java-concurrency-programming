package com.example.demo.atomicexample原子性包.actimic;

import com.example.demo.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ThreadSafe
public class AtomicReferenceFieldUpdaterExample {

    /**
     * AtomicIntegerFieldUpdater 他的类的核心 是用原子性来更新类的实例的某一个字段 是一个原子性的修改
     * 这个字段要求必须是用volatile 同时还不能是static修饰的字段
     */
    private static AtomicIntegerFieldUpdater<AtomicReferenceFieldUpdaterExample> updater 
            = AtomicIntegerFieldUpdater.newUpdater(AtomicReferenceFieldUpdaterExample.class, "count");
    
    @Getter
    public volatile int count = 100;
    
    public static void main(String[] args) {

        AtomicReferenceFieldUpdaterExample example = new AtomicReferenceFieldUpdaterExample();
        
        // 如果当前变量的值为100 的时候更新为 120 
        if (updater.compareAndSet(example, 100, 120)){
            log.info("update success, 1 ======= {}", example.getCount());
        }

        if (updater.compareAndSet(example, 100, 120)){
            log.info("update success, 2 ========{}", example.getCount());
        }else{
            log.info("update failed, {}", example.getCount());
        }
    }
    
}
