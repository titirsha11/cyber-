package com.example.cybershield.api;

import com.example.cybershield.ai.OpenNlpPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiApiController {
    @Autowired
    private OpenNlpPatternService openNlpPatternService;

    // Train the AI model with suspect and normal samples
    @PostMapping("/train")
    public ResponseEntity<String> trainModel(@RequestBody TrainRequest request) {
        try {
            openNlpPatternService.trainModel(request.getSuspectSamples(), request.getNormalSamples());
            return ResponseEntity.ok("Model trained successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Training failed: " + e.getMessage());
        }
    }

    // Check if a message is suspect using the trained AI model
    @PostMapping("/check")
    public ResponseEntity<Boolean> checkMessage(@RequestBody CheckRequest request) {
        boolean result = openNlpPatternService.isSuspect(request.getText());
        return ResponseEntity.ok(result);
    }

    // DTOs for requests
    public static class TrainRequest {
        private List<String> suspectSamples;
        private List<String> normalSamples;
        public List<String> getSuspectSamples() { return suspectSamples; }
        public void setSuspectSamples(List<String> suspectSamples) { this.suspectSamples = suspectSamples; }
        public List<String> getNormalSamples() { return normalSamples; }
        public void setNormalSamples(List<String> normalSamples) { this.normalSamples = normalSamples; }
    }
    public static class CheckRequest {
        private String text;
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }
}
