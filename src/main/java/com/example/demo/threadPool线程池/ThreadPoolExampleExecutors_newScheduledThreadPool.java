package com.example.demo.threadPool线程池;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExampleExecutors_newScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

//        scheduledExecutorService.schedule(() -> {
//            log.info("schedule run ");
//        }, 3, TimeUnit.SECONDS);

        // 在线程池调用创建之后延迟1秒之后，每隔3秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.warn("schedule run ");
        }, 1, 3, TimeUnit.SECONDS);
        
//        scheduledExecutorService.shutdown();

        /**
         * 用Timer来做调度延迟  相当于定时器
         */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.warn("timer run");
            }
        }, new Date(), 5 * 1000);
    }
    
}
