package org.example.service.executor;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RequiredArgsConstructor
public class SettableFuture<T> implements Future<T> {

    public static <K> SettableFuture<K> create(Listener<K> listener) {
        return new SettableFuture<>(listener);
    }

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private boolean isCancelled = false;
    private T object;
    private Exception exception;
    private final Listener<T> listener;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        readWriteLock.writeLock().lock();
        try {
            this.isCancelled = true;
            return false;
        } catch (Exception ig) {
            return false;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public boolean isDone() {
        readWriteLock.readLock().lock();
        try {
            return this.object != null;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        readWriteLock.readLock().lock();
        try {
            return this.object;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void set(T object) {
        readWriteLock.writeLock().lock();
        try {
            if (exception != null) return;
            this.object = object;
            this.listener.settableSet(object, null);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void setError(Exception e) {
        readWriteLock.writeLock().lock();
        try {
            if (object != null) return;
            this.exception = e;
            this.listener.settableSet(null, e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
