package com.example.demo.singleton;

import com.example.demo.annotations.ThreadSafe;

/**
 * 饿汉模式
 *  单例的实例在类加载的时候创建
 *  饿汉模式是线程安全的
 *  如果单例类的构造方法中没有包含过多的操作处理，饿汉模式是可以接受的
 *  缺点：
 *      如果构造方法中包含过多的操作处理，会导致这个类加载特别慢，可能会导致性能问题
 *      如果使用饿汉模式只进行类的加载，并没有进行类的调用，这就会造成类的浪费
 */
@ThreadSafe
public class SingletonExample饿汉模式 {
    
    // 私有构造器
    private SingletonExample饿汉模式(){
        
    }
    
    // 单例对象
    /**
     * 这里通过静态域直接初始化
     */
    private static SingletonExample饿汉模式 instance = new SingletonExample饿汉模式();
    
    // 静态工厂方法获取单例对象
    public static SingletonExample饿汉模式 getInstance(){
        return instance;
    }
    
}
