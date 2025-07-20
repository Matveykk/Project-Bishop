package com.weyland.service;

import com.weyland.audit.annotation.WeylandWatchingYou;
import com.weyland.model.CommandRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommandExecutor {

    @WeylandWatchingYou
    public void execute(CommandRequest command) {
        log.info("Выполняется команда: {}", command.getDescription());
        try {
            Thread.sleep(10_000);
            log.info("Команда: {} - выполнена", command.getDescription());
        } catch (InterruptedException e) {
            log.error("Ошибка при выполнении задачи: ", e);
        }
    }
}
