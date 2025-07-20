package com.weyland.metrics.controller;

import lombok.RequiredArgsConstructor;
import com.weyland.metrics.service.MetricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MetricsController {
    private final MetricsService metricsService;

    @GetMapping("/com/weyland/metrics/queue-size")
    public int getQueueSize() {
        return metricsService.getCurrentQueueSize();
    }

    @GetMapping("/com/weyland/metrics/completed-tasks/{author}")
    public int getCompletedTasksForAuthor(@PathVariable String author) {
        return metricsService.getCompletedTasksForAuthor(author);
    }

    @GetMapping("/com/weyland/metrics/current-task")
    public String getCurrentTask() {
        return metricsService.getCurrentTask();
    }
}
