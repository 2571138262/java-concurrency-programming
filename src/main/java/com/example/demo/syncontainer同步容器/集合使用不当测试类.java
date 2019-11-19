package com.example.demo.syncontainer同步容器;

import java.util.Iterator;
import java.util.Vector;
import java.util.stream.Stream;

public class 集合使用不当测试类 {

    /**
     * 使用forEach循环删除集合中的元素
     * @param v1
     */
    private static void test1(Vector<Integer> v1){
        for (Integer i : v1){
            if (i.equals(3)){
                v1.remove(i);
            }
        }
    }

    /**
     * 使用迭代器移除集合中的元素
     * @param v1
     */
    private static void test2(Vector<Integer> v1){
        Iterator<Integer> iterator = v1.iterator();
        while (iterator.hasNext()){
            Integer i = iterator.next();
            if (i.equals(3)){
                v1.remove(i);
            }
        }
    }

    /**
     * 使用传统的for循环移除集合中的元素   成功
     * @param v1
     */
    public static void test3(Vector<Integer> v1){
        for (int i = 0; i < v1.size(); i ++){
            if (v1.get(i).equals(3)){
                v1.remove(i);
            }
        }
    }

    /**
     * 使用lambda表达式遍历
     * @param v1
     */
    public static void test4(Vector<Integer> v1){
        Stream.of(v1).filter(i -> i.equals(3)).map(i -> v1.remove(i)).forEach(System.out::println);
//        v1.forEach(i -> {
//            if (i.equals(3)){
//                v1.remove(i);
//            }
//        });
    }
    
    public static void main(String[] args) {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        test1(vector); // java.util.ConcurrentModificationException
//        test2(vector); // java.util.ConcurrentModificationException
//        test3(vector);
        test4(vector); // java.util.ConcurrentModificationException
    }
    
}
