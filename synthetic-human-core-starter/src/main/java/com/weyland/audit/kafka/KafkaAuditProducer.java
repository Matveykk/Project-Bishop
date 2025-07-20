package com.weyland.audit.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaAuditProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${audit.kafka.topic}")
    private String topic;

    public void send(String message) {
        kafkaTemplate.send(topic, message);
    }
}
