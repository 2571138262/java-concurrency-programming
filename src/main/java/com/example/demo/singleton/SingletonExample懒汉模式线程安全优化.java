package com.example.demo.singleton;

import com.example.demo.annotations.NotThreadSafe;

/**
 * 懒汉模式  -> 双重同步锁单例模式
 * 单例的实例在第一次使用的时候创建
 */

@NotThreadSafe
public class SingletonExample懒汉模式线程安全优化 {

    // 私有构造器
    private SingletonExample懒汉模式线程安全优化() {

    }

    // 单例对象
    private static SingletonExample懒汉模式线程安全优化 instance = null;

    // 静态工厂方法获取单例对象

    /**
     * 这种写法同样是线程不安全的 
     *      细节原因：从CPU的指令来说起
     *          执行 instance = new SingletonExample懒汉模式线程安全优化();  的时候
     *          1、memory = allocate() 分配对象的内存空间
     *          2、ctorInstance()  初始化对象
     *          3、instance = memory  设置instance指向刚分配的内存
     *          
     *      JVM 和 CPU 优化，发生了指令重排 (多线程下)
     *          1、memory = allocate() 分配对象的内存空间
     *          3、instance = memory  设置instance指向刚分配的内存
     *          2、ctorInstance()  初始化对象
     * @return
     */
    public static SingletonExample懒汉模式线程安全优化 getInstance() {
        if (instance == null) { // 双重检测机制 + 锁                   // 线程B 发现instance 有值了， 直接return 
            synchronized (SingletonExample懒汉模式线程安全优化.class) {
                if (instance == null) {
                    instance = new SingletonExample懒汉模式线程安全优化(); // 线程A  - 第3部
                }
            }
        }
        return instance;
    }

}
