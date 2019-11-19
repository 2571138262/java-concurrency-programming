package com.example.demo.singleton;

import com.example.demo.annotations.Recommend;
import com.example.demo.annotations.ThreadSafe;

/**
 * 通过枚举的方式得到单例， 利用JVM的特性达到最安全， 推荐使用
 *      优势:     相对懒汉模式， 更加安全
 *                相对饿汉模式， 在实际调用的时候才开始初始化
 */
@ThreadSafe
@Recommend
public class SingletonExample枚举模式 {
    
    // 私有构造器
    private SingletonExample枚举模式(){}
    
    public static SingletonExample枚举模式 getInstance(){
        return  Singleton.INSTANCE.getInstance();
    }
    
    private enum Singleton{
        INSTANCE;
        
        private SingletonExample枚举模式 singleton;

        /**
         * JVM 保证这个方法绝对只被调用一次
         */
        Singleton(){
            singleton = new SingletonExample枚举模式();
        }
        
        public SingletonExample枚举模式 getInstance(){
            return singleton;
        }
    }
    
}
