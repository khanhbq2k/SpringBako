//package org.example.service;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//
//@Configuration
//public class ThreadPoolTaskSchedulerConfig {
//
//    @Bean("booking.csTicketExecutor")
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//        threadPoolTaskScheduler.setPoolSize(2);
//        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
//        threadPoolTaskScheduler.initialize();
//        return threadPoolTaskScheduler;
//    }
//}