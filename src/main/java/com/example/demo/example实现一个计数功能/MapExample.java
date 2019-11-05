package com.example.demo.example实现一个计数功能;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class MapExample {
    
    private static Map<Integer, Integer> map = Maps.newHashMap();
    
    // 每次最多200个请求
    private static int threadNum = 200;
    // 一共5000次请求
    private static int clientNum = 5000;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadNum);
        for (int index = 0; index < clientNum; index ++){
            final int threadNum = index;
            exec.execute(() -> {
                try{
                    semaphore.acquire();
                    func(threadNum);
                    semaphore.release();
                }catch (Exception e){
                    log.error("=========================exception", e);
                }
            });
        }
        exec.shutdown();
        log.info("+==============================size: {}", map.size());
    }

    private static void func(int threadNum) {
        map.put(threadNum, threadNum);
    }


}
