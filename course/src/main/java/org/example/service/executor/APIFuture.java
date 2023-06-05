package org.example.service.executor;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class APIFuture {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public <T> SettableFuture<T> submit(Callable<T> task, Listener<T> listener) {
        SettableFuture<T> settableFuture = SettableFuture.create(listener);
        executorService.submit(() -> {
            try {
                settableFuture.set(task.call());
            } catch (Exception e) {
                settableFuture.setError(e);
            }
        }, executorService);
        return settableFuture;
    }

}
