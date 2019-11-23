package com.example.demo.aqsJUC工具类核心组件.future及相关线程类;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do somethings in callable");
                Thread.sleep(5000);
                return "Callable Done";
            }
        });

        FutureTask<String> futureTask1 = new FutureTask<>(new Runnable(){
            @Override
            public void run() {
                log.info("do somethings in runnable");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Runnable Done"); 
        
        new Thread(futureTask).start();
        log.info("do somethings in main");
        Thread.sleep(1000);

        new Thread(futureTask1).start();
        log.info("do somethings in main222222");
        Thread.sleep(1000);

        // 从FutureTask对象中取得任务结果 
        String result = futureTask.get();
        log.info("result: {}", result);

        String result1 = futureTask1.get();
        log.info("result1: {}", result1);
    }
    
    
}
