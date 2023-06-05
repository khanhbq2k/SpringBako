package org.example.service.executor;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ExecutorBako {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ListeningExecutorService lExecutorService = MoreExecutors.listeningDecorator(executorService);

    ListenableFuture<Integer> asynctask = lExecutorService.submit(() -> {
        TimeUnit.MILLISECONDS.sleep(500);
        return 5;
    });


    public ListenableFutureTask<String> fetchConfigListenableTask(String configKey) {
        return ListenableFutureTask.create(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return String.format("%s.%d", configKey, new Random().nextInt(Integer.MAX_VALUE));
        });
    }
}
