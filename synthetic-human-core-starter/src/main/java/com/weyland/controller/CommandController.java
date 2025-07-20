package com.weyland.controller;

import com.weyland.audit.annotation.WeylandWatchingYou;
import com.weyland.model.CommandRequest;
import com.weyland.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/command")
@RequiredArgsConstructor
public class CommandController {

    private final CommandService commandService;

    @PostMapping
    public ResponseEntity<String> receiveCommand(@RequestBody CommandRequest request) {
        commandService.processCommand(request);
        return ResponseEntity.ok("Команда принята");
    }
}
