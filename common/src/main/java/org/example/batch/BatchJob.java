package org.example.batch;

import java.time.Duration;
import java.util.Collection;

public interface BatchJob<T> {

    String getJobId();

    Duration getFixedClear();

    int getBatchSize();

    void run(Collection<T> objects);
}
