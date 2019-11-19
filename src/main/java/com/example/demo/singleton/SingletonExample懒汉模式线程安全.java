package com.example.demo.singleton;

import com.example.demo.annotations.NotThreadSafe;
import com.example.demo.annotations.ThreadSafe;

/**
 * 懒汉模式
 *  单例的实例在第一次使用的时候创建
 */
@ThreadSafe
public class SingletonExample懒汉模式线程安全 {
    
    // 私有构造器
    private SingletonExample懒汉模式线程安全(){
        
    }
    
    // 单例对象
    private static SingletonExample懒汉模式线程安全 instance = null;
    
    // 静态工厂方法获取单例对象

    /**
     * 这种写法不推荐，
     * 对整个方法进行加锁，会影响到性能
     * @return
     */
    public static synchronized SingletonExample懒汉模式线程安全 getInstance(){
        if (instance == null){
            instance = new SingletonExample懒汉模式线程安全();
        }
        return instance;
    }
    
}
