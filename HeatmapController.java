package com.example.cybershield.controller;

import com.example.cybershield.model.DetectionEvent;
import com.example.cybershield.repository.DetectionEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/heatmap")
public class HeatmapController {
    @Autowired
    private DetectionEventRepository eventRepo;

    @GetMapping
    public List<DetectionEvent> getHeatmapEvents() {
        return eventRepo.findAll();
    }
}
