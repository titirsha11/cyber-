package com.example.cybershield.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatternRecognitionService {
    private static final List<String> ANTI_INDIA_TERMS = List.of(
            "anti-india", "xyz", "abc", "bannedword1", "bannedword2" // Add more
    );

    public boolean isSuspect(String text) {
        if (text == null) return false;
        return ANTI_INDIA_TERMS.stream().anyMatch(term -> text.toLowerCase().contains(term));
    }
}
