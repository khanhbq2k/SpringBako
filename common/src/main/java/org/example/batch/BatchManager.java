package org.example.batch;

import org.springframework.context.Lifecycle;

import java.util.List;

public interface BatchManager extends Lifecycle {

    void addToBatch(BatchJob<?> batchJob, List<?> data);
}
