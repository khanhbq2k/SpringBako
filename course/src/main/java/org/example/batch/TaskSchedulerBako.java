package org.example.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class TaskSchedulerBako {

    private final TaskScheduler taskScheduler;
    private final Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    @Autowired
    private void init() {
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);
        queue.add(7);
        queue.add(8);
        queue.add(9);
    }

    @PostConstruct
    private void execute() {
        System.out.println("Now: " + new Date());
        ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleAtFixedRate(() -> {
            if (!queue.isEmpty()) {
                for (int i = 0; i < 2; i++) {
                    System.out.println("Poll time " + new Date());
                    System.out.println(queue.poll());
                }
            }
        }, 2000);
    }
}
