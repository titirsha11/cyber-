package com.example.cybershield.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AiApiClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AiApiClient(RestTemplate restTemplate, @Value("${ai.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String trainModel(List<String> suspectSamples, List<String> normalSamples) {
        Map<String, Object> body = new HashMap<>();
        body.put("suspectSamples", suspectSamples);
        body.put("normalSamples", normalSamples);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/train", entity, String.class);
        return response.getBody();
    }

    public boolean checkMessage(String text) {
        Map<String, Object> body = new HashMap<>();
        body.put("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(baseUrl + "/check", entity, Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }
}