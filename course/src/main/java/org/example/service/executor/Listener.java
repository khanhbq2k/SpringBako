package org.example.service.executor;

public interface Listener<T> {

    void settableSet(T result, Exception e);
}
