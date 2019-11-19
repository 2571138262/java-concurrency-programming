package com.example.demo.immutable不可变;

import com.example.demo.annotations.ThreadSafe;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * Collections.unmodifiableXXX()
 */
@Slf4j
@ThreadSafe
public class ImmutableExampleCollectionsunmodifiableXXX {
    
    
    private static Map<Integer, Integer> map = Maps.newHashMap();     
    
    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);

        /**
         * 源码中将对当前map所有的更新操作都重写了， 直接抛出了异常
         */
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
//        a = 2;
        
//        b = "2";
        
//        map = Maps.newHashMap();
        
        map.put(1, 3); // 会报错， Collections.unmodifiableMap() 处理过的Map中的元素是不能修改的
        
        log.info("========{}", map.get(1));
    }
    
    private void test(final int x){
//        x = 1;
        
    }
    
}
