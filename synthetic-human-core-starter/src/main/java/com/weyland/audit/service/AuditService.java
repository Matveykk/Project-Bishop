package com.weyland.audit.service;

import com.weyland.audit.kafka.KafkaAuditProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditService {

    @Value("${audit.mode}")
    private String mode;

    private final KafkaAuditProducer kafkaAuditProducer;

    public void audit(String methodName, Object[] args, Throwable error) {
        String message = String.format("AUDIT — Метод: %s, Аргументы: %s",
                methodName,
                Arrays.toString(args));

        if ("kafka".equalsIgnoreCase(mode)) {
            kafkaAuditProducer.send(message);
        } else {
            log.info(message);
        }
    }
}
