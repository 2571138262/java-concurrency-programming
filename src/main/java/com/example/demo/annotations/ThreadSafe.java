package com.example.demo.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解  
 *      自定义注解一般需要指定俩个属性 
 *      - @Target(ElementType.TYPE) 标明当前自定义注解作用的范围
 *          ElementType.TYPE 范围 是 类
 *      - @Retention(RetentionPolicy.SOURCE) 标明当前自定义注解存在的范围
 *          RetentionPolicy.SOURCE      使用SOURCE在编译的时候就会被忽略掉
 *          RetentionPolicy.CLASS       
 *          RetentionPolicy.RUNTIME     注解会在class的字节码中存在， 通过反射可以拿到 
 *          
 *          
 * 当前注解目的是标记一下项目中线程安全的类
 */
@Target(ElementType.TYPE) 
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadSafe {
    
    String value() default "";
    
}
