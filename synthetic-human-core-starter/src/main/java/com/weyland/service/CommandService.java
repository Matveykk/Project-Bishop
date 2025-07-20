package com.weyland.service;

import com.weyland.audit.annotation.WeylandWatchingYou;
import com.weyland.error.exception.CommandValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.weyland.metrics.service.MetricsService;
import com.weyland.model.CommandRequest;
import com.weyland.model.Priority;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandService {

    private final CommandQueue queue;
    private final CommandExecutor executor;
    private final MetricsService metricsService;

    @WeylandWatchingYou
    public void processCommand(CommandRequest commandRequest){
        validate(commandRequest);

        if(commandRequest.getPriority() == Priority.CRITICAL) {
            executor.execute(commandRequest);
        } else {
            queue.submit(commandRequest);
        }
        metricsService.incrementCompletedTasks(commandRequest.getAuthor());
    }

    private void validate(CommandRequest commandRequest) {
        if(commandRequest.getDescription() == null || commandRequest.getDescription().length() > 1000) {
            throw new CommandValidationException("Описание команды некорректно");
        }
        if(commandRequest.getAuthor() == null || commandRequest.getAuthor().length() > 100) {
            throw new CommandValidationException("Автор команды некорректен");
        }
        if(!(commandRequest.getPriority() == Priority.COMMON) && !(commandRequest.getPriority() == Priority.CRITICAL)) {
            throw new CommandValidationException("Приоритет команды некорректен");
        }
    }
}
