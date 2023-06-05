package org.example.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class BatchJobManager implements SmartLifecycle, BatchManager {

    private boolean isRunning;
    private final TaskScheduler taskScheduler;
    private final List<BatchJob<?>> batchJobList = new ArrayList<>();
    private final Map<String, ConcurrentLinkedQueue<?>> queueMap = new ConcurrentHashMap<>();
    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();

    public void register(BatchJob<?> batchJob) {
        batchJobList.add(batchJob);
    }

    private ConcurrentLinkedQueue<?> getQueue(BatchJob<?> batchJob) {
        return queueMap.computeIfAbsent(batchJob.getJobId(), (k) -> new ConcurrentLinkedQueue<>());
    }

    private ScheduledFuture<?> getScheduledFuture(BatchJob<?> batchJob) {
        return scheduledFutureMap.get(batchJob.getJobId());
    }

    @Override
    public void addToBatch(BatchJob<?> batchJob, List<?> data) {
        ConcurrentLinkedQueue queue = getQueue(batchJob);
        queue.addAll(data);
        if (queue.size() >= batchJob.getBatchSize()) {
            executeBatchJob(batchJob, false);
            rescheduleTask(batchJob, queue);
        } else if (!scheduledFutureMap.containsKey(batchJob.getJobId())) {
            scheduleTask(batchJob, queue);
        }
    }

    private synchronized void rescheduleTask(BatchJob<?> batchJob, ConcurrentLinkedQueue<?> queue) {
        ScheduledFuture<?> scheduledFuture = getScheduledFuture(batchJob);
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
        scheduleTask(batchJob, queue);
    }

    private synchronized void executeBatchJob(BatchJob batchJob, boolean flush) {
        ConcurrentLinkedQueue<?> queue = getQueue(batchJob);
        if (!isRunning || queue.isEmpty()) {
            return;
        }
        List list = new ArrayList<>();
        while (!queue.isEmpty()) {
            if (!flush && queue.size() < batchJob.getBatchSize()) {
                break;
            }
            int elementsToAdd = Math.min(batchJob.getBatchSize(), queue.size());
            for (int i = 0; i < elementsToAdd; i++) {
                list.add(queue.poll());
            }
            batchJob.run(list);
            list.clear();
        }
//        scheduleTask(jobId, batchJob, queue);
    }

    private void scheduleTask(BatchJob<?> batchJob, ConcurrentLinkedQueue<?> queue) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleAtFixedRate(
                () -> {
                    if (queue.size() > 0) {
                        executeBatchJob(batchJob, true);
                    }
                },
                Instant.now().plus(batchJob.getFixedClear()),
                batchJob.getFixedClear()
        );
        scheduledFutureMap.put(batchJob.getJobId(), scheduledFuture);
    }

    @Override
    public void start() {
        isRunning = true;
    }

    @Override
    public void stop() {
        for (BatchJob<?> batchJob : batchJobList) {
            executeBatchJob(batchJob, true);
        }
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}
