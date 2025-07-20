package com.weyland.metrics.service;


import com.weyland.service.CommandQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final CommandQueue commandQueue;

    private final ConcurrentHashMap<String, AtomicInteger> tasksCompletedByAuthor = new ConcurrentHashMap<>();

    public int getCurrentQueueSize() {
        return commandQueue.getQueueSize();
    }

    public void incrementCompletedTasks(String author) {
        tasksCompletedByAuthor
                .computeIfAbsent(author, k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    public int getCompletedTasksForAuthor(String author) {
        return tasksCompletedByAuthor.getOrDefault(author, new AtomicInteger(0)).get();
    }

    public String getCurrentTask() {
        return commandQueue.getCurrentTask().toString();
    }
}
