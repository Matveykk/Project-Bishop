package com.weyland.service;

import com.weyland.error.exception.QueueOverflowException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.weyland.model.CommandRequest;
import org.springframework.stereotype.Component;
import java.util.concurrent.LinkedBlockingQueue;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandQueue {

    private static final int MAX_QUEUE_SIZE = 100;
    private final BlockingQueue<CommandRequest> queue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);
    private final CommandExecutor executor;
    private final ExecutorService worker = Executors.newSingleThreadExecutor();

    @Getter
    private CommandRequest currentTask;

    public void submit(CommandRequest command) {
        boolean offered = queue.offer(command);
        if (!offered) {
            throw new QueueOverflowException("QUEUE_OVERFLOW: Очередь заполнена");
        }
    }

    @PostConstruct
    public void initWorker() {
        worker.submit(() -> {
            while (true) {
                try {
                    CommandRequest task = queue.take();
                    executor.execute(task);
                    currentTask = task;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("Ошибка при исполнении команды из очереди: ", e);
                }
            }
        });
    }

    public int getQueueSize() {
        return queue.size();
    }
}
