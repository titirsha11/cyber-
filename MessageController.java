package com.example.cybershield.controller;

import com.example.cybershield.model.Message;
import com.example.cybershield.model.DetectionEvent;
import com.example.cybershield.repository.MessageRepository;
import com.example.cybershield.repository.DetectionEventRepository;
import com.example.cybershield.service.PatternRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private PatternRecognitionService patternService;
    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private DetectionEventRepository eventRepo;

    @PostMapping
    public ResponseEntity<?> postMessage(@RequestBody Message msg) {
        msg.setTimestamp(LocalDateTime.now());
        messageRepo.save(msg);
        if (patternService.isSuspect(msg.getContent())) {
            DetectionEvent event = DetectionEvent.builder()
                    .message(msg)
                    .reason("Suspect content detected")
                    .detectedAt(LocalDateTime.now())
                    .latitude(msg.getLatitude())
                    .longitude(msg.getLongitude())
                    .build();
            eventRepo.save(event);
            // TODO: Trigger popup/alert logic here
        }
        return ResponseEntity.ok().build();
    }
}
