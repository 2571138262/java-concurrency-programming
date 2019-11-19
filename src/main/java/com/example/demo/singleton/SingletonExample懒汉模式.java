package com.example.demo.singleton;

import com.example.demo.annotations.NotThreadSafe;

/**
 * 懒汉模式
 *  单例的实例在第一次使用的时候创建
 */
@NotThreadSafe
public class SingletonExample懒汉模式 {
    
    // 私有构造器
    private SingletonExample懒汉模式(){
        
    }
    
    // 单例对象
    private static SingletonExample懒汉模式 instance = null;
    
    // 静态工厂方法获取单例对象
    public static SingletonExample懒汉模式 getInstance(){
        if (instance == null){
            instance = new SingletonExample懒汉模式();
        }
        return instance;
    }
    
}
