//package org.example.service;
//
//import lombok.SneakyThrows;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class ScheduleExecutorTe {
//
//    public static final ScheduledExecutorService pushMessageExecutor = Executors.newScheduledThreadPool(3);
//
////    private final ThreadPoolTaskScheduler executorService;
////
////    public ScheduleExecutorTe(@Qualifier("booking.csTicketExecutor") ThreadPoolTaskScheduler executorService) {
////        this.executorService = executorService;
////    }
//
//    @Async
//    @Scheduled(fixedDelay = 1000)
//    public void testBlockThread2() throws InterruptedException {
//        System.out.println("Init1 " + System.currentTimeMillis());
//        Thread.sleep(5000);
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void testBlockThread() throws InterruptedException {
//        System.out.println("Init2 " + System.currentTimeMillis());
//        Thread.sleep(5000);
//    }
//
//    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
//    public void push() {
//        System.out.println("push " + System.currentTimeMillis());
////        for (int i = 0; i < 3; i++) {
////            executorService.schedule(() -> {
////                System.out.println(System.currentTimeMillis());
////                try {
////                    Thread.sleep(2000);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////            }, new Date(System.currentTimeMillis() + 3000));
////        }
//    }
//
//    @SneakyThrows
//    public static void main(String[] args) {
//
//        for (int i = 0; i < 3; i++) {
//            pushMessageExecutor.schedule(() -> {
//                System.out.println(System.currentTimeMillis());
//                try {
//                    // do some heavy work
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }, 3, TimeUnit.SECONDS);
//        }
//    }
//
//}
