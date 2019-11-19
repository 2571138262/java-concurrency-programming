package com.example.demo.syncontainer同步容器;

import com.example.demo.annotations.NotThreadSafe;

import java.util.Vector;

/**
 * Vector 虽然能保证同一个时刻只有一个线程能使用他，但是不能保证俩个同步方法 (remove() 和 get())是线程安全的，
 *          同一时刻因为get() 和 remove() 方法的执行顺序导致数组下标越界
 */
@NotThreadSafe
public class VectorExample验证同步容器不一定是线程安全的 {
    
    private static Vector<Integer> vector = new Vector<>();
    
    public static void main(String[] args){
        while (true){
            for (int i = 0; i < 10; i ++){
                vector.add(i);
            }

            Thread thread = new Thread(){
                @Override
                public void run(){
                    for (int i = 0; i < vector.size(); i ++){
                        vector.remove(i);
                    }
                }
            };
            
            Thread thread1 = new Thread(){
                @Override
                public void run(){
                    for (int i = 0; i < vector.size(); i ++){
                        vector.get(i);
                    }
                }
            };
            
            thread.start();
            thread1.start();
        }
    }
    
}
