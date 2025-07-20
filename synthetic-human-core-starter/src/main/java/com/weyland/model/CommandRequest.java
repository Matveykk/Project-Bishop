package com.weyland.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Data
public class CommandRequest {
    private String description;
    private Priority priority;
    private String author;
    private String time;

    public CommandRequest() {
        this.time = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
