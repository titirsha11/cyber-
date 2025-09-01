package com.example.cybershield.repository;

import com.example.cybershield.model.DetectionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetectionEventRepository extends JpaRepository<DetectionEvent, Long> {
}
