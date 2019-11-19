package com.example.demo.immutable不可变;

import com.example.demo.annotations.ThreadSafe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * 不可变对象
 */
@ThreadSafe
public class ImmutableExampleGuavaImmutableXXX对象 {
    
    private final static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
    
    private final static ImmutableSet set = ImmutableSet.copyOf(list);
    
    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1, 2, 3, 4, 5, 6);

    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder().put(1, 2).put(3, 4).put(5, 6).put(7, 8).build();

    public static void main(String[] args) {
//        list.add(4);
//        set.add(4);
//        map.put(1, 4);
//        map2.put(1, 4);

        System.out.println(map2.get(3));
    }
    
}
