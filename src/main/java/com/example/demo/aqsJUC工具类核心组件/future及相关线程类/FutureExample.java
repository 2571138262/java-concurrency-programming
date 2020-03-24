package com.example.demo.aqsJUC工具类核心组件.future及相关线程类;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureExample {

    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            log.info("do somethings in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }

    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            log.info("do somethings in callable");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 声明线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 让线程池直接提交任务，返回Future对象
        Future<String> future = exec.submit(new MyCallable());
        Future<String> future1 = exec.submit(new MyRunnable(), "Done");
        log.info("do somethings in main");
        Thread.sleep(1000);
         
        // 从Future对象中取得任务结果 
        String result = future.get();
        log.info("result: {}", result);
        String result1 = future1.get();
        log.info("result1: {}", result1);
    }
    
}
